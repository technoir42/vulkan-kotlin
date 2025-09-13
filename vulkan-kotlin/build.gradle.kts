import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
    id("io.github.technoir42.conventions.kotlin-multiplatform-library")
}

kotlinMultiplatformLibrary {
    defaultTargets = false

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
        commonMain.dependencies {
            api(project(":volk-kotlin"))
            api(libs.kotlinx.io.core)
        }
        commonTest.dependencies {
            implementation(kotlin("test"))
        }
    }
}

tasks.withType<AbstractTestTask>().configureEach {
    testLogging {
        showStandardStreams = true
        events(TestLogEvent.PASSED, TestLogEvent.FAILED)
    }
}
