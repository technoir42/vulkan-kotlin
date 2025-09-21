plugins {
    id("io.technoirlab.conventions.kotlin-multiplatform-library")
}

kotlinMultiplatformLibrary {
    packageName = "io.technoirlab.vulkan"
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
            api(libs.kotlinx.datetime)
            api(libs.kotlinx.io.core)
        }
    }
}
