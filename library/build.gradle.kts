plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("maven-publish")
}

android {
    namespace = "com.rsix.library"
    compileSdk = 33

    defaultConfig {
        minSdk = 24
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11 // 指定源代码的兼容性
        targetCompatibility = JavaVersion.VERSION_11 // 指定目标平台的兼容性
    }
    kotlinOptions{
        jvmTarget = "11"
    }
    afterEvaluate {
        publishing {
            publications {
                create<MavenPublication>("release") {
                    from(components["release"])
                    groupId = "com.rsix.droidkit.library" // 依赖库的组 id
                    artifactId = "library" // 依赖库的名称（jitpack 都不会使用到）
                    version = "1.0.0"
                }
            }
        }
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.10.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.8.0")
}