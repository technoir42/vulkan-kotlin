package sample

import io.technoirlab.volk.VK_INSTANCE_CREATE_ENUMERATE_PORTABILITY_BIT_KHR
import io.technoirlab.volk.VK_KHR_PORTABILITY_ENUMERATION_EXTENSION_NAME
import io.technoirlab.volk.VK_VERSION_MAJOR
import io.technoirlab.volk.VK_VERSION_MINOR
import io.technoirlab.volk.VK_VERSION_PATCH
import io.technoirlab.vulkan.Device
import io.technoirlab.vulkan.Instance
import io.technoirlab.vulkan.Vulkan
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.toCStringArray
import kotlinx.cinterop.toKString
import kotlin.experimental.ExperimentalNativeApi

@OptIn(ExperimentalNativeApi::class)
class Sample : AutoCloseable {
    private val vulkan = Vulkan()
    private var instance: Instance? = null
    private var device: Device? = null

    fun run() = memScoped {
        val vulkanVersion = vulkan.instanceVersion
        println("Vulkan version: ${VK_VERSION_MAJOR(vulkanVersion)}.${VK_VERSION_MINOR(vulkanVersion)}.${VK_VERSION_PATCH(vulkanVersion)}")

        val instanceExtensions = vulkan.enumerateInstanceExtensionProperties()
        println("Supported instance extensions: ${instanceExtensions.joinToString(", ") { it.extensionName.toKString() }}")

        val instanceLayers = vulkan.enumerateInstanceLayerProperties()
        println("Supported instance layers: ${instanceLayers.joinToString(", ") { it.layerName.toKString() }}")

        val extensions = buildList {
            if (Platform.osFamily.isAppleFamily) {
                add(VK_KHR_PORTABILITY_ENUMERATION_EXTENSION_NAME)
            }
        }

        val instance = vulkan.createInstance(instanceInfo = {
            if (VK_KHR_PORTABILITY_ENUMERATION_EXTENSION_NAME in extensions) {
                flags = flags or VK_INSTANCE_CREATE_ENUMERATE_PORTABILITY_BIT_KHR
            }
            ppEnabledExtensionNames = extensions.toCStringArray(memScope)
            enabledExtensionCount = extensions.size.toUInt()
        }).also { instance = it }

        println("Created Vulkan instance")

        val physicalDevices = instance.enumeratePhysicalDevices().map { it to it.getProperties().deviceName.toKString() }
        println("Supported physical devices: ${physicalDevices.joinToString(", ") { it.second }}")

        val (physicalDevice, deviceName) = physicalDevices.firstOrNull() ?: error("No physical devices found")

        val deviceExtensions = physicalDevice.enumerateDeviceExtensionProperties()
        println("Supported device extensions: ${deviceExtensions.joinToString(", ") { it.extensionName.toKString() }}")

        device = physicalDevice.createDevice()
        println("Created logical device for $deviceName")
    }

    override fun close() {
        device?.close()
        instance?.close()
        vulkan.close()
    }

    private val OsFamily.isAppleFamily: Boolean
        get() = when (this) {
            OsFamily.MACOSX, OsFamily.IOS, OsFamily.TVOS, OsFamily.WATCHOS -> true
            else -> false
        }
}
