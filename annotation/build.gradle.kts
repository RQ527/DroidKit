plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")

}
apply("./stub-lib.gradle.kts")
java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}
kotlin {
    jvmToolchain(8)
}
dependencies{
    val stub = tasks.named<Jar>("stubLibsJar").get().outputs.files
    compileOnly(stub)
}