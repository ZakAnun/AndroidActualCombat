plugins {
    id("java-library")
    id("com.android.lint")
    id("kotlin")
}

lintOptions {
    mapOf<String, Any>(
        "checkDependencies" to true,
        "textReport" to true
    )
}

dependencies {

    implementation("com.android.tools.lint:lint-api:30.2.1")
    implementation("com.android.tools.lint:lint-checks:30.2.1")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.6.21")

    testImplementation("junit:junit:4.13.2")
    testImplementation("com.android.tools.lint:lint:30.2.1")
    testImplementation("com.android.tools.lint:lint-tests:30.2.1")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}