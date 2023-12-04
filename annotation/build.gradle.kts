plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")

}
apply("./stub-lib.gradle.kts")
java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(8))
    }
}
kotlin {
    jvmToolchain(8)
}
dependencies{
    val stub = tasks.named<Jar>("stubLibsJar").get().outputs.files
    compileOnly(stub)
}