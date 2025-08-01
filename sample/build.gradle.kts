plugins {
    id("io.github.technoir42.conventions.native-application")
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
