plugins {
    id("io.github.technoir42.conventions.kotlin-multiplatform-application")
}

kotlinMultiplatformApplication {
    defaultTargets = false
}

kotlin {
    iosArm64()
    iosSimulatorArm64()
    linuxArm64()
    linuxX64()
    macosArm64()
    mingwX64()

    sourceSets.commonMain {
        dependencies {
            implementation(project(":volk-kotlin"))
        }
    }
}
