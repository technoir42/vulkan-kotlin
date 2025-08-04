pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        mavenLocal()
        maven("https://maven.pkg.github.com/technoir42/gradle-convention-plugins") {
            credentials {
                username = providers.gradleProperty("gpr.user").orNull
                password = providers.gradleProperty("gpr.token").orNull
            }
        }
    }
    plugins {
        val conventionPluginsVersion = "v17"
        id("io.github.technoir42.conventions.kotlin-multiplatform-application") version conventionPluginsVersion
        id("io.github.technoir42.conventions.kotlin-multiplatform-library") version conventionPluginsVersion
        id("io.github.technoir42.conventions.settings") version conventionPluginsVersion
    }
}

plugins {
    id("io.github.technoir42.conventions.kotlin-multiplatform-application") apply false
    id("io.github.technoir42.conventions.kotlin-multiplatform-library") apply false
    id("io.github.technoir42.conventions.settings")
}

globalSettings {
    projectId = "volk-kotlin"
}

include(":volk")
include(":sample")
