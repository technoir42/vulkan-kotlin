plugins {
    id("io.github.technoir42.conventions.kotlin-multiplatform-application")
}

kotlinMultiplatformApplication {
    defaultTargets = false
}

kotlin {
    iosArm64()
    iosSimulatorArm64()
    linuxX64()
    macosArm64()
    mingwX64()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":volk"))
            }
        }
    }
}
