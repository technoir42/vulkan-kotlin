Vulkan Kotlin
=============

Kotlin/Native bindings for Vulkan API.

# Supported targets

* iosArm64
* iosSimulatorArm64
* linuxArm64
* linuxX64
* macosArm64
* mingwX64

## Usage

### Add vulkan-kotlin repository

1. Add your personal GitHub credentials to `~/.gradle/gradle.properties`:
    ```properties
    gpr.user=your-github-username
    gpr.token=your-personal-access-token
    ```

2. Add this repository's package registry to your project's Maven repositories in `settings.gradle.kts`:
    ```kotlin
    dependencyResolutionManagement {
        repositories {
            maven("https://maven.pkg.github.com/technoir42/vulkan-kotlin") {
                name = "vulkan-kotlin"
                credentials {
                    username = providers.gradleProperty("gpr.user").get()
                    password = providers.gradleProperty("gpr.token").get()
                }
            }
        }
    }
    ```

### Using Vulkan API via Volk

Add Volk Kotlin dependency to your project:

```kotlin
kotlin {
   sourceSets.commonMain {
       dependencies {
           implementation("io.github.technoir42:volk-kotlin:<version>")
       }
   }
}
```

You can now initialize Volk and use Vulkan API from Kotlin:

   ```kotlin
   import volk.volkInitialize
   
   val result = volkInitialize()
   ```

# License

This library is available to anybody free of charge, under the terms of MIT License.
