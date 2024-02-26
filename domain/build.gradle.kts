plugins {
    kotlin("jvm")
    kotlin("kapt")
}
java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}
kotlin {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(8))
    }
}
dependencies {
    implementation(project(":data"))
    implementation(libs.kotlin)
    implementation(libs.coroutines)

    testImplementation(libs.bundles.test)

    implementation(libs.bundles.dagger)
    kapt(libs.dagger.compiler)
    kapt(libs.dagger.android.processor)
}