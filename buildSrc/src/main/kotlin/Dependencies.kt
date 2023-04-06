/**
 * 版本号信息记录
 */
object Version {
    const val compileSdkVersion = 30
    const val buildToolsVersion = "30.0.3"
    const val mindSdkVersion = 21
    const val targetSdkVersion = 30
    const val versionCode = 1
    const val versionName = "1.0.0"
    const val jvmTarget = "1.8"
}

object Kotlin {
    const val version = "1.7.22"
    const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib:$version"
}

object AndroidX {
    const val coreKtx = "androidx.core:core-ktx:1.6.0"
    const val appcompat = "androidx.appcompat:appcompat:1.3.1"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.1.0"
    const val recyclerView = "androidx.recyclerview:recyclerview:1.2.1"
    const val pagingRuntime = "androidx.paging:paging-runtime:3.0.0-alpha02"
    const val runtime = "androidx.fragment:fragment:1.3.0-alpha06"
    const val runtimeKtx = "androidx.fragment:fragment-ktx:1.3.0-alpha06"
    const val testing = "androidx.fragment:fragment-testing:1.3.0-alpha06"
    const val appStartup = "androidx.startup:startup-runtime:1.0.0"

    const val testExtJunit = "androidx.test.ext:junit:1.1.3"
    const val testEspressoCore = "androidx.test.espresso:espresso-core:3.4.0"
}

object Material {
    const val material = "com.google.android.material:material:1.4.0"
}

object Junit {
    const val junit = "junit:junit:4.13.2"
}

object Lint {
    private const val version = "30.2.0"
    const val lint = "com.android.tools.lint:lint:$version"
    const val lintTest = "com.android.tools.lint:lint-tests:$version"
}

object AacGradle {
    const val version = "7.4.2"
}

object Hilt {
    const val pluginVersion = "2.28.1-alpha"
    private const val version = "2.28-alpha"
    private const val viewModel = "1.0.0-alpha01"
    const val daggerRuntime = "com.google.dagger:hilt-android:${version}"
    const val daggerCompiler = "com.google.dagger:hilt-android-compiler:${version}"
    const val viewModule = "androidx.hilt:hilt-lifecycle-viewmodel:${viewModel}"
    const val compiler = "androidx.hilt:hilt-compiler:${viewModel}"
}

object Room {
    private const val version = "2.3.0-alpha01"
    const val runtime = "androidx.room:room-runtime:${version}"
    const val compiler = "androidx.room:room-compiler:${version}"
    const val ktx = "androidx.room:room-ktx:${version}"
    const val rxjava2 = "androidx.room:room-rxjava2:${version}"
    const val testing = "androidx.room:room-testing:${version}"
}

object Retrofit {
    private const val version = "2.9.0"
    private const val okhttpVersion = "4.9.0"
    const val runtime = "com.squareup.retrofit2:retrofit:${version}"
    const val gson = "com.squareup.retrofit2:converter-gson:${version}"
    const val mock = "com.squareup.retrofit2:retrofit-mock:${version}"
    const val logging = "com.squareup.okhttp3:logging-interceptor:${okhttpVersion}"
}

object ThirdDep {
    const val glide = "com.github.bumptech.glide:glide:4.12.0"
    const val timber = "com.jakewharton.timber:timber:5.0.1"
}