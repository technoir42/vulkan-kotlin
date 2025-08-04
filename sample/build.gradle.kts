plugins {
    id("io.github.technoir42.conventions.kotlin-multiplatform-application")
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":volk"))
            }
        }
    }
}
