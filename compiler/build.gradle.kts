plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
    id("kotlin-kapt")
    id("maven-publish")
}
java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}
kotlin {
    jvmToolchain(8)
}
publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            groupId = "com.rsix.droidkit.compiler" // 依赖库的组 id
            artifactId = "compiler" // 依赖库的名称
            version = "1.0.0"
        }
    }
}
dependencies {
    implementation(project(":annotation"))
    // https://mvnrepository.com/artifact/com.google.auto.service/auto-service
    implementation("com.google.auto.service:auto-service:1.1.1")
    kapt("com.google.auto.service:auto-service:1.1.1")
    implementation("com.google.devtools.ksp:symbol-processing-api:1.9.21-1.0.15")
    // https://mvnrepository.com/artifact/com.squareup/kotlinpoet
    implementation("com.squareup:kotlinpoet:1.15.1")
    implementation("com.squareup:kotlinpoet-ksp:1.15.1")
}