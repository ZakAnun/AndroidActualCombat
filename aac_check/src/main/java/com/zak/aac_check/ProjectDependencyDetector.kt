package com.zak.aac_check

import com.android.tools.lint.detector.api.*
import org.jetbrains.uast.UElement
import org.jetbrains.uast.ULiteralExpression

/**
 * description
 *
 * @author liminglin
 * @date 2022/5/31.
 */
@Suppress("UnstableApiUsage")
class ProjectDependencyDetector: Detector(), Detector.UastScanner {

    private val nodeMap = HashMap<String, ElementNode>()
    // 允许配置
    private val specificRootNode = "RootProject"

    companion object {
        val ISSUE: Issue = Issue.create(
            id = "ProjectDependencyDetector",
            briefDescription = "app dependency relationship",
            explanation = """
                        app dependency relationship
                        """.trimIndent(),
            category = Category.CORRECTNESS,
            priority = 9,
            severity = Severity.FATAL,
            androidSpecific = true,
            implementation = Implementation(
                ProjectDependencyDetector::class.java,
                Scope.JAVA_FILE_SCOPE
            )
        )
    }

    override fun getApplicableUastTypes(): List<Class<out UElement>> {
        return listOf(ULiteralExpression::class.java)
    }

    override fun getApplicableMethodNames(): List<String> {
        return listOf("ProjectDependencyDetector")
    }

    override fun beforeCheckRootProject(context: Context) {
        collectNode(context.project.name)
        println("beforeCheckRootProject, 收集 ${context.project.name} 信息结束")
        super.beforeCheckRootProject(context)
    }

    override fun beforeCheckEachProject(context: Context) {
        if (context.project.isExternalLibrary) {
            return
        }
        analysisCurrentProjectDependency(project = context.project)
        println("beforeCheckEachProject 分析 ${context.project.name} 信息结束")
        super.beforeCheckEachProject(context)
    }

    override fun afterCheckRootProject(context: Context) {
        nodeMap[specificRootNode]?.run {
            if (context.project.name == specificRootNode &&
                dependencyNodeList.size != nodeMap.size - 1) { // 如果节点结果集只有根节点的子节点个数的话，也不输出结果
                generateResult()
            }
        }
        println("afterCheckRootProject 生成 ${context.project.name} 结果结束")
        super.afterCheckRootProject(context)
    }

    /**
     * 记录节点信息
     */
    private fun collectNode(moduleName: String): ElementNode {
        return if (nodeMap.contains(moduleName)) {
            nodeMap[moduleName] ?: ElementNode().apply {
                this.moduleName = moduleName
                nodeMap[moduleName] = this
            }
        } else {
            ElementNode().apply {
                this.moduleName = moduleName
                nodeMap[moduleName] = this
            }
        }
    }

    /**
     * 分析当前项目依赖
     */
    private fun analysisCurrentProjectDependency(project: Project) {
        val currentNode = collectNode(moduleName = project.name)
        val list = project.directLibraries.filter { !it.isExternalLibrary }
        list.forEach { pro ->
            val childNode = collectNode(moduleName = pro.name)
            if (!currentNode.dependencyNodeList.map { it.moduleName }.contains(childNode.moduleName)) {
                currentNode.dependencyNodeList.add(childNode)
            }
        }
    }

    /**
     * 生成结果
     */
    private fun generateResult() {
        // 允许配置
        for (elementNode: ElementNode in nodeMap.values) {
            removeUnnecessaryDependency(elementNode = elementNode)
        }
        println("生成结果，当前 map size == ${nodeMap.size}")
        println("```mermaid")
        val head = "graph TD"
        println(head)
        for (element: ElementNode in nodeMap.values) {
            val currentName = element.moduleName
            for (childElement: ElementNode in element.dependencyNodeList) {
                println("${currentName}[${currentName}]-->${childElement.moduleName}[${childElement.moduleName}]")
            }
        }
        println("```")
    }

    /**
     * 移除不必要依赖
     */
    private fun removeUnnecessaryDependency(elementNode: ElementNode) {
        val dependencyList = elementNode.dependencyNodeList
        val tempDependencyList = ArrayList(dependencyList)
        val iterator = tempDependencyList.iterator()
        while (iterator.hasNext()) {
            val targetElement = iterator.next()
            val leftTargetElements = tempDependencyList.filter { it != targetElement }
            for (otherItem: ElementNode in leftTargetElements) {
                if (containNode(rootNode = otherItem, targetNode = targetElement)) {
                    dependencyList.remove(targetElement)
                    continue
                }
            }
        }
    }

    /**
     * rootNode 下是否包含 targetNode 节点
     */
    private fun containNode(rootNode: ElementNode, targetNode: ElementNode): Boolean {
        if (rootNode == targetNode) {
            return true
        }
        val rootChildren = rootNode.dependencyNodeList
        for (child: ElementNode in rootChildren) {
            if (rootNode == child) {
                return true
            } else {
                val result = containNode(rootNode = child, targetNode = targetNode)
                if (result) {
                    return result
                }
            }
        }
        return false
    }

}

/**
 * 描述 module 的节点
 */
class ElementNode(var moduleName: String = "", var dependencyNodeList: MutableList<ElementNode> = mutableListOf())