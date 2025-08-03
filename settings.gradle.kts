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
        val conventionPluginsVersion = "v16"
        id("io.github.technoir42.conventions.native-application") version conventionPluginsVersion
        id("io.github.technoir42.conventions.native-library") version conventionPluginsVersion
        id("io.github.technoir42.conventions.settings") version conventionPluginsVersion
    }
}

plugins {
    id("io.github.technoir42.conventions.native-application") apply false
    id("io.github.technoir42.conventions.native-library") apply false
    id("io.github.technoir42.conventions.settings")
}

globalSettings {
    projectId = "volk-kotlin"
}

include(":volk")
include(":sample")
