plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
    id("maven-publish")
}
apply("./stub-lib.gradle.kts")
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
            groupId = "com.rsix.droidkit.annotation" // 依赖库的组 id
            artifactId = "annotation" // 依赖库的名称（jitpack 都不会使用到）
            version = "1.0.0"
        }
    }
}

dependencies {
    val stub = tasks.named<Jar>("stubLibsJar").get().outputs.files
    compileOnly(stub)
}