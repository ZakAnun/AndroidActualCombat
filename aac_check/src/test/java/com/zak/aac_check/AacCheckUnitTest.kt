package com.zak.aac_check

import com.android.tools.lint.checks.infrastructure.ProjectDescription
import com.android.tools.lint.checks.infrastructure.TestFiles.java
import com.android.tools.lint.checks.infrastructure.TestLintTask.lint
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@Suppress("UnstableApiUsage")
class AacCheckUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun testFileLink() {
        lint().files(
            java(
                """
                    package test.pkg;
                    public class TestClass2 {
                        public static final String name = "zak";
                    }
                """
            ).indented()
        )
            .issues(StringLintDetector.ISSUE)
            .run()
            .expect(
                """
                src/test/pkg/TestClass2.java:3: Information: This code mentions zak: Congratulations [StringLintDetector]
                    public static final String name = "zak";
                                                      ~~~~~
                0 errors, 0 warnings
                """
            )
    }

    @Test
    fun testDependencyNormal() {
        lint().projects(
            ProjectDescription().apply {
                name = "RootProject"
                dependsOn(ProjectDescription().apply {
                    name = "OneProject"
                    dependsOn(ProjectDescription().apply {
                        name = "OneProjectDependence"
                    })
                    dependsOn(ProjectDescription().apply {
                        name = "OneProjectDependenceBetter"
                    })
                })
                dependsOn(ProjectDescription().apply {
                    name = "TwoProject"
                })
                dependsOn(ProjectDescription().apply {
                    name = "ThreeProject"
                    dependsOn(ProjectDescription().apply {
                        name = "ThreeProjectDependence"
                    })
                })
                dependsOn(ProjectDescription().apply {
                    name = "FourProject"
                    dependsOn(ProjectDescription().apply {
                        name = "FourProjectDependence"
                    })
                })
            }
        )
            .issues(ProjectDependencyDetector.ISSUE)
            .run()
            .expect(
                """
                    No warnings.
                """
            )
    }
}