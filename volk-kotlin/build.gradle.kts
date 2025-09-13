import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.jetbrains.kotlin.gradle.targets.native.tasks.KotlinNativeHostTest
import org.jetbrains.kotlin.gradle.targets.native.tasks.KotlinNativeTest

plugins {
    id("io.github.technoir42.conventions.kotlin-multiplatform-library")
}

kotlinMultiplatformLibrary {
    packageName = "volk"
    defaultTargets = false
    xcFrameworks.from(moltenVkXcFramework)

    buildFeatures {
        cinterop = true
    }
}

kotlin {
    iosArm64()
    iosSimulatorArm64()
    linuxArm64()
    linuxX64()
    macosArm64()
    mingwX64()

    sourceSets {
        commonTest.dependencies {
            implementation(kotlin("test"))
        }
    }
}

tasks.withType<KotlinNativeTest>().configureEach {
    testLogging {
        showStandardStreams = true
        events(TestLogEvent.PASSED, TestLogEvent.FAILED)
    }
}

private val moltenVkXcFramework: Provider<File>
    get() = providers.environmentVariable("VULKAN_SDK").map {
        File(it, "lib").resolve("MoltenVK.xcframework")
    }
