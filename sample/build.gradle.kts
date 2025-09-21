plugins {
    id("io.technoirlab.conventions.kotlin-multiplatform-application")
}

kotlin {
    androidNativeArm64()
    iosArm64()
    iosSimulatorArm64()
    linuxArm64()
    linuxX64()
    macosArm64()
    mingwX64()

    sourceSets.commonMain {
        dependencies {
            implementation(project(":vulkan-kotlin"))
        }
    }
}
