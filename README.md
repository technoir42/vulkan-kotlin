Volk Kotlin
===========

Kotlin/Native bindings for [Volk](https://github.com/zeux/volk), meta loader for Vulkan API.

# Supported targets

* iosArm64
* iosSimulatorArm64
* linuxArm64
* linuxX64
* macosArm64
* mingwX64

# Usage

1. Add your personal GitHub credentials to `~/.gradle/gradle.properties`:
    ```properties
    gpr.user=your-github-username
    gpr.token=your-personal-access-token
    ```

2. Add this repository's package registry to your project's Maven repositories in `settings.gradle.kts`:
    ```kotlin
    dependencyResolutionManagement {
        repositories {
            maven("https://maven.pkg.github.com/technoir42/volk-kotlin") {
                name = "volk-kotlin"
                credentials {
                    username = providers.gradleProperty("gpr.user").get()
                    password = providers.gradleProperty("gpr.token").get()
                }
            }
        }
    }
    ```

3. Add Volk Kotlin dependency:
   ```kotlin
   kotlin {
       sourceSets {
           val commonMain by getting {
               dependencies {
                   implementation("io.github.technoir42:volk:<volk version>")
               }
           }
       }
   }
   ```

4. Use Volk bindings from Kotlin:
   ```kotlin
   import volk.volkInitialize
   
   val result = volkInitialize()
   ```

# License

This library is available to anybody free of charge, under the terms of MIT License.
