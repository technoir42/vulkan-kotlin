plugins {
    id("io.github.technoir42.conventions.kotlin-multiplatform-library")
}

kotlinMultiplatformLibrary {
    defaultTargets = false
}

kotlin {
    androidNativeArm64()
    iosArm64()
    iosSimulatorArm64()
    linuxArm64()
    linuxX64()
    macosArm64()
    mingwX64()

    sourceSets {
        commonMain.dependencies {
            api(project(":volk-kotlin"))
            api(libs.kotlinx.io.core)
        }
    }
}
