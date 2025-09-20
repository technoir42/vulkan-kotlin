Vulkan Kotlin
=============

[![Build](https://github.com/technoir42/vulkan-kotlin/actions/workflows/build.yaml/badge.svg?branch=main)](https://github.com/technoir42/vulkan-kotlin/actions/workflows/build.yaml)

Kotlin/Native bindings for Vulkan API.

# Supported targets

* androidNativeArm64
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

### Using Vulkan API wrapper

Add Vulkan Kotlin dependency to your project:

```kotlin
kotlin {
   sourceSets.commonMain {
       dependencies {
           implementation("io.github.technoir42:vulkan-kotlin:<version>")
       }
   }
}
```

# License

```
   Copyright 2025 Sergei Chelombitko

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
```
