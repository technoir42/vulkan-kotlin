package sample

import kotlinx.cinterop.memScoped
import kotlinx.cinterop.toCStringArray
import kotlinx.cinterop.toKString
import volk.VK_INSTANCE_CREATE_ENUMERATE_PORTABILITY_BIT_KHR
import volk.VK_KHR_PORTABILITY_ENUMERATION_EXTENSION_NAME
import volk.VK_VERSION_MAJOR
import volk.VK_VERSION_MINOR
import volk.VK_VERSION_PATCH
import vulkan.kotlin.Context
import vulkan.kotlin.Device
import vulkan.kotlin.Instance
import kotlin.experimental.ExperimentalNativeApi

@OptIn(ExperimentalNativeApi::class)
class Sample : AutoCloseable {
    private val context = Context()
    private var instance: Instance? = null
    private var device: Device? = null

    fun run() = memScoped {
        val vulkanVersion = context.instanceVersion
        println("Vulkan version: ${VK_VERSION_MAJOR(vulkanVersion)}.${VK_VERSION_MINOR(vulkanVersion)}.${VK_VERSION_PATCH(vulkanVersion)}")

        val instanceExtensions = context.enumerateInstanceExtensionProperties()
        println("Supported instance extensions: ${instanceExtensions.joinToString(", ") { it.extensionName.toKString() }}")

        val instanceLayers = context.enumerateInstanceLayerProperties()
        println("Supported instance layers: ${instanceLayers.joinToString(", ") { it.layerName.toKString() }}")

        val extensions = buildList {
            if (Platform.osFamily.isAppleFamily) {
                add(VK_KHR_PORTABILITY_ENUMERATION_EXTENSION_NAME)
            }
        }

        val instance = context.createInstance(instanceInfo = {
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
        context.close()
    }

    private val OsFamily.isAppleFamily: Boolean
        get() = when (this) {
            OsFamily.MACOSX, OsFamily.IOS, OsFamily.TVOS, OsFamily.WATCHOS -> true
            else -> false
        }
}
