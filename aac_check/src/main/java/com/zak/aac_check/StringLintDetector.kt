package com.zak.aac_check

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.*
import org.jetbrains.uast.UElement
import org.jetbrains.uast.UExpressionList
import org.jetbrains.uast.ULiteralExpression
import org.jetbrains.uast.evaluateString

/**
 * description
 *
 * @author liminglin @Shenzhen Youzan Technology Co.Ltd
 * @date 2022/5/31.
 */
@Suppress("UnstableApiUsage")
class StringLintDetector: Detector(), Detector.UastScanner {

    companion object {
        val ISSUE: Issue = Issue.create(
            id = "StringLintDetector",
            briefDescription = "Zak Mentions",
            explanation = """
                    This check highlights string literals in code which mentions the word `lint`. \
                    Blah blah blah.

                    Another paragraph here.
                    """, // no need to .trimIndent(), lint does that automatically
            category = Category.CORRECTNESS,
            priority = 6,
            severity = Severity.INFORMATIONAL,
            implementation = Implementation(
                StringLintDetector::class.java,
                Scope.JAVA_FILE_SCOPE
            )
        )
    }

    override fun getApplicableUastTypes(): List<Class<out UElement>> {
        return listOf(ULiteralExpression::class.java)
    }

    override fun createUastHandler(context: JavaContext): UElementHandler {
        return object : UElementHandler() {
            override fun visitLiteralExpression(node: ULiteralExpression) {
                val string = node.evaluateString() ?: return
                if (string.contains("zak") && string.matches(Regex(".*\\bzak\\b.*"))) {
                    context.report(
                        ISSUE, node, context.getLocation(node),
                        "This code mentions `zak`: **Congratulations**"
                    )
                }
            }
        }
    }
}