package sample

import volk.VK_SUCCESS
import volk.VK_VERSION_MAJOR
import volk.VK_VERSION_MINOR
import volk.VK_VERSION_PATCH
import volk.volkGetInstanceVersion
import volk.volkInitialize

fun main() {
    val result = volkInitialize()
    if (result != VK_SUCCESS) {
        println("volkInitialize failed!")
        return
    }

    val version = volkGetInstanceVersion()
    val versionString = "${VK_VERSION_MAJOR(version)}.${VK_VERSION_MINOR(version)}.${VK_VERSION_PATCH(version)}"
    println("Vulkan version $versionString initialized.")
}
