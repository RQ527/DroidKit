import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.jvm.tasks.Jar
import java.util.Properties

fun getAndroidJar(): String {
    var androidSdkDir: String? = System.getenv("ANDROID_SDK_HOME")
    if (androidSdkDir.isNullOrBlank()) {
        val propertiesFile = rootProject.file("local.properties")
        if (propertiesFile.exists()) {
            val properties = Properties()
            propertiesFile.inputStream().use {
                properties.load(it)
            }
            androidSdkDir = properties.getProperty("sdk.dir") as String?
        }
    }
    if (androidSdkDir.isNullOrBlank()) {
        throw StopExecutionException("Please declare your 'sdk.dir' in the 'local.properties' file.")
    }
    val path = "platforms${File.separator}android-${33}${File.separator}android.jar"
    return File(androidSdkDir, path).absolutePath
}

tasks.register("stubLibs", JavaCompile::class) {
    source = fileTree("src/stub/java")
    classpath = project.files(getAndroidJar())
    destinationDir = project.file("${project.buildDir}/tmp/stubLibs")
}

tasks.register("stubLibsJar", org.gradle.api.tasks.bundling.Jar::class) {
    archiveBaseName.set("stub")
    version = "1.0"
    from(tasks.named("stubLibs"))
    include("**/*.class")
}
