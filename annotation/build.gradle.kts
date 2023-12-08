plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")

}
apply("./stub-lib.gradle.kts")
java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}
kotlin {
    jvmToolchain(11)
}
dependencies{
    val stub = tasks.named<Jar>("stubLibsJar").get().outputs.files
    compileOnly(stub)
}