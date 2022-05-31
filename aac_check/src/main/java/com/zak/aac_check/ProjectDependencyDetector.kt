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

    companion object {
        val ISSUE: Issue = Issue.create(
            id = "ProjectDependencyDetector",
            briefDescription = "app dependency relationship",
            explanation = """
                        app dependency relationship
                        """,
            category = Category.CORRECTNESS,
            priority = 6,
            severity = Severity.WARNING,
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
        return listOf("a")
    }

    override fun beforeCheckRootProject(context: Context) {
        super.beforeCheckRootProject(context)
        println("beforeCheckRootProject, project = ${context.project.name}")
    }

    override fun beforeCheckEachProject(context: Context) {
        super.beforeCheckEachProject(context)
        println("beforeCheckEachProject, project = ${context.project.name}")
    }

    override fun afterCheckRootProject(context: Context) {
        super.afterCheckRootProject(context)
        println("afterCheckRootProject, project = ${context.project.name}")
    }

    override fun afterCheckEachProject(context: Context) {
        super.afterCheckEachProject(context)
        println("afterCheckEachProject, project = ${context.project.name}")
    }
}