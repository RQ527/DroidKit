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
                    artifactId = "library" // 依赖库的名称
                    version = "1.0.0"
                }
            }
        }
    }
    dataBinding {
        enable = true
    }
}

dependencies {
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.1")
}