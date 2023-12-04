plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
    id("kotlin-kapt")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(8))
    }
}
kotlin {
    // For example:
    jvmToolchain(8)
}

dependencies {
    implementation(project(":annotation"))
    // https://mvnrepository.com/artifact/com.google.auto.service/auto-service
    implementation("com.google.auto.service:auto-service:1.1.1")
    kapt("com.google.auto.service:auto-service:1.1.1")
    implementation("com.google.devtools.ksp:symbol-processing-api:1.9.20-1.0.13")
    // https://mvnrepository.com/artifact/com.squareup/kotlinpoet
    implementation("com.squareup:kotlinpoet:1.15.1")
    implementation("com.squareup:kotlinpoet-ksp:1.15.1")
}