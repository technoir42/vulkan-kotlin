import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.konan.target.Family
import org.jetbrains.kotlin.konan.target.HostManager

plugins {
    id("io.technoirlab.conventions.kotlin-multiplatform-library")
}

kotlinMultiplatformLibrary {
    packageName = "volk"
    defaultTargets = false

    buildFeatures {
        cinterop = true
    }
}


kotlin {
    androidNativeArm64()
    iosArm64()
    iosSimulatorArm64()
    linuxArm64()
    linuxX64()
    macosArm64()
    mingwX64()

    targets.withType<KotlinNativeTarget>().configureEach {
        if (konanTarget.family == Family.ANDROID) {
            compilations.configureEach {
                cinterops.configureEach {
                    tasks.named(interopProcessingTaskName) { dependsOn(generateVfsOverlayTask) }
                    compilerOpts("-ivfsoverlay", overlayFile.get().asFile.absolutePath)
                }
            }
        }
    }
}

private val overlayFile: Provider<RegularFile>
    get() = layout.buildDirectory.file("vulkan/vfsoverlay.json")
private val konanDataDir: Provider<File>
    get() = providers.environmentVariable("KONAN_DATA_DIR").map { File(it) }
        .orElse(providers.systemProperty("user.home").map { File(it, ".konan") })
private val androidNdkSysrootDir: Provider<File>
    get() = konanDataDir.map { it.resolve("dependencies/target-toolchain-2-${HostManager.hostOs()}-android_ndk/sysroot") }
private val androidNdkVulkanIncludeDir: Provider<File>
    get() = androidNdkSysrootDir.map { it.resolve("usr/include/vulkan") }
private val vulkanSdkIncludeDir: Provider<File>
    get() = providers.environmentVariable("VULKAN_SDK")
        .map { File(it, "${if (HostManager.hostIsMingw) "Include" else "include"}/vulkan") }

private val generateVfsOverlayTask = tasks.register<GenerateVfsOverlayTask>("generateVfsOverlay") {
    fromDir.set(layout.dir(androidNdkVulkanIncludeDir))
    toDir.set(layout.dir(vulkanSdkIncludeDir))
    outputOverlayFile.set(overlayFile)
}

@DisableCachingByDefault
abstract class GenerateVfsOverlayTask : DefaultTask() {
    @get:Internal
    abstract val fromDir: DirectoryProperty

    @get:Internal
    abstract val toDir: DirectoryProperty

    @get:OutputFile
    abstract val outputOverlayFile: RegularFileProperty

    @TaskAction
    fun generate() {
        val json = """
            {
              "version": 0,
              "roots": [
                { "name": "${fromDir.get().asFile.normalizedPath()}",
                  "type": "directory-remap",
                  "external-contents": "${toDir.get().asFile.normalizedPath()}"
                }
              ]
            }
        """.trimIndent()
        val out = outputOverlayFile.get().asFile
        out.parentFile.mkdirs()
        out.writeText(json)
    }

    private fun File.normalizedPath(): String =
        absolutePath.replace("\\", "/")
}
