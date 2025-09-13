package vulkan.kotlin

import kotlinx.cinterop.MemScope
import kotlinx.cinterop.UIntVar
import kotlinx.cinterop.alloc
import kotlinx.cinterop.allocArray
import kotlinx.cinterop.get
import kotlinx.cinterop.invoke
import kotlinx.cinterop.ptr
import kotlinx.cinterop.value
import volk.VK_STRUCTURE_TYPE_DEVICE_CREATE_INFO
import volk.VK_STRUCTURE_TYPE_FORMAT_PROPERTIES_2
import volk.VK_STRUCTURE_TYPE_IMAGE_FORMAT_PROPERTIES_2
import volk.VK_STRUCTURE_TYPE_PHYSICAL_DEVICE_FEATURES_2
import volk.VK_STRUCTURE_TYPE_PHYSICAL_DEVICE_IMAGE_FORMAT_INFO_2
import volk.VK_STRUCTURE_TYPE_PHYSICAL_DEVICE_PROPERTIES_2
import volk.VK_STRUCTURE_TYPE_PHYSICAL_DEVICE_VULKAN_1_3_FEATURES
import volk.VK_STRUCTURE_TYPE_PHYSICAL_DEVICE_VULKAN_1_4_FEATURES
import volk.VK_TRUE
import volk.VkBool32Var
import volk.VkDeviceCreateInfo
import volk.VkDeviceVar
import volk.VkExtensionProperties
import volk.VkFormat
import volk.VkFormatProperties2
import volk.VkImageFormatProperties2
import volk.VkLayerProperties
import volk.VkPhysicalDevice
import volk.VkPhysicalDeviceFeatures
import volk.VkPhysicalDeviceFeatures2
import volk.VkPhysicalDeviceImageFormatInfo2
import volk.VkPhysicalDeviceMemoryProperties
import volk.VkPhysicalDeviceProperties
import volk.VkPhysicalDeviceProperties2
import volk.VkPhysicalDeviceVulkan13Features
import volk.VkPhysicalDeviceVulkan14Features
import volk.VkPresentModeKHR
import volk.VkPresentModeKHRVar
import volk.VkQueueFamilyProperties
import volk.VkSurfaceCapabilitiesKHR
import volk.VkSurfaceFormatKHR
import volk.vkCreateDevice
import volk.vkEnumerateDeviceExtensionProperties
import volk.vkEnumerateDeviceLayerProperties
import volk.vkGetPhysicalDeviceFeatures2
import volk.vkGetPhysicalDeviceFormatProperties2
import volk.vkGetPhysicalDeviceImageFormatProperties2
import volk.vkGetPhysicalDeviceMemoryProperties
import volk.vkGetPhysicalDeviceProperties
import volk.vkGetPhysicalDeviceProperties2
import volk.vkGetPhysicalDeviceQueueFamilyProperties
import volk.vkGetPhysicalDeviceSurfaceCapabilitiesKHR
import volk.vkGetPhysicalDeviceSurfaceFormatsKHR
import volk.vkGetPhysicalDeviceSurfacePresentModesKHR
import volk.vkGetPhysicalDeviceSurfaceSupportKHR

class PhysicalDevice(val handle: VkPhysicalDevice) {
    /**
     * Create a new device instance.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCreateDevice.html">vkCreateDevice</a>
     */
    context(memScope: MemScope)
    fun createDevice(
        createInfo: VkDeviceCreateInfo.() -> Unit = {},
        features: VkPhysicalDeviceFeatures.() -> Unit = {},
        features13: VkPhysicalDeviceVulkan13Features.() -> Unit = {},
        features14: VkPhysicalDeviceVulkan14Features.() -> Unit = {}
    ): Device {
        val features14 = memScope.alloc<VkPhysicalDeviceVulkan14Features> {
            sType = VK_STRUCTURE_TYPE_PHYSICAL_DEVICE_VULKAN_1_4_FEATURES
            features14()
        }
        val features13 = memScope.alloc<VkPhysicalDeviceVulkan13Features> {
            sType = VK_STRUCTURE_TYPE_PHYSICAL_DEVICE_VULKAN_1_3_FEATURES
            pNext = features14.ptr
            features13()
        }
        val features = memScope.alloc<VkPhysicalDeviceFeatures2> {
            sType = VK_STRUCTURE_TYPE_PHYSICAL_DEVICE_FEATURES_2
            pNext = features13.ptr
            this.features.features()
        }
        val deviceCreateInfo = memScope.alloc<VkDeviceCreateInfo> {
            sType = VK_STRUCTURE_TYPE_DEVICE_CREATE_INFO
            pNext = features.ptr
            createInfo()
        }
        val deviceVar = memScope.alloc<VkDeviceVar>()
        vkCreateDevice!!(handle, deviceCreateInfo.ptr, null, deviceVar.ptr)
            .checkResult("Failed to create a device")
        return Device(deviceVar.value!!)
    }

    /**
     * Enumerate the extensions supported by the device.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkEnumerateDeviceExtensionProperties.html">vkEnumerateDeviceExtensionProperties</a>
     */
    context(memScope: MemScope)
    fun enumerateDeviceExtensionProperties(): Sequence<VkExtensionProperties> {
        val countVar = memScope.alloc<UIntVar>()
        vkEnumerateDeviceExtensionProperties!!(handle, null, countVar.ptr, null)

        val count = countVar.value.toInt()
        if (count == 0) return emptySequence()

        val extensionProperties = memScope.allocArray<VkExtensionProperties>(count)
        vkEnumerateDeviceExtensionProperties!!(handle, null, countVar.ptr, extensionProperties)

        return (0 until count).asSequence().map { extensionProperties[it] }
    }

    /**
     * Enumerate the layers supported by the device.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkEnumerateDeviceLayerProperties.html">vkEnumerateDeviceLayerProperties</a>
     */
    context(memScope: MemScope)
    fun enumerateDeviceLayerProperties(): Sequence<VkLayerProperties> {
        val countVar = memScope.alloc<UIntVar>()
        vkEnumerateDeviceLayerProperties!!(handle, countVar.ptr, null)

        val count = countVar.value.toInt()
        if (count == 0) return emptySequence()

        val layerProperties = memScope.allocArray<VkLayerProperties>(count)
        vkEnumerateDeviceLayerProperties!!(handle, countVar.ptr, layerProperties)

        return (0 until count).asSequence().map { layerProperties[it] }
    }

    /**
     * Retrieve the fine-grained features that can be supported by the physical device.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkGetPhysicalDeviceFeatures2.html">vkGetPhysicalDeviceFeatures2</a>
     */
    context(memScope: MemScope)
    fun getFeatures(): Features {
        val features14 = memScope.alloc<VkPhysicalDeviceVulkan14Features> {
            sType = VK_STRUCTURE_TYPE_PHYSICAL_DEVICE_VULKAN_1_4_FEATURES
        }
        val features13 = memScope.alloc<VkPhysicalDeviceVulkan13Features> {
            sType = VK_STRUCTURE_TYPE_PHYSICAL_DEVICE_VULKAN_1_3_FEATURES
            pNext = features14.ptr
        }
        val features = memScope.alloc<VkPhysicalDeviceFeatures2> {
            sType = VK_STRUCTURE_TYPE_PHYSICAL_DEVICE_FEATURES_2
            pNext = features13.ptr
        }
        vkGetPhysicalDeviceFeatures2!!(handle, features.ptr)
        return Features(features, features13, features14)
    }

    /**
     * Retrieve properties of a format supported by the physical device.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkGetPhysicalDeviceFormatProperties2.html">vkGetPhysicalDeviceFormatProperties2</a>
     */
    context(memScope: MemScope)
    fun getFormatProperties(format: VkFormat): VkFormatProperties2 {
        val properties = memScope.alloc<VkFormatProperties2> {
            sType = VK_STRUCTURE_TYPE_FORMAT_PROPERTIES_2
        }
        vkGetPhysicalDeviceFormatProperties2!!(handle, format, properties.ptr)
        return properties
    }

    /**
     * Retrieve properties of an image format applied to a particular type of image resource.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkGetPhysicalDeviceImageFormatProperties2.html">vkGetPhysicalDeviceImageFormatProperties2</a>
     */
    context(memScope: MemScope)
    fun getImageFormatProperties(formatInfo: VkPhysicalDeviceImageFormatInfo2.() -> Unit): VkImageFormatProperties2 {
        val imageFormatInfo = memScope.alloc<VkPhysicalDeviceImageFormatInfo2> {
            sType = VK_STRUCTURE_TYPE_PHYSICAL_DEVICE_IMAGE_FORMAT_INFO_2
            formatInfo()
        }
        val properties = memScope.alloc<VkImageFormatProperties2> {
            sType = VK_STRUCTURE_TYPE_IMAGE_FORMAT_PROPERTIES_2
        }
        vkGetPhysicalDeviceImageFormatProperties2!!(handle, imageFormatInfo.ptr, properties.ptr)
            .checkResult("Failed to get image format properties")
        return properties
    }

    /**
     * Retrieve properties of the physical device's memory.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkGetPhysicalDeviceMemoryProperties.html">vkGetPhysicalDeviceMemoryProperties</a>
     */
    context(memScope: MemScope)
    fun getMemoryProperties(): VkPhysicalDeviceMemoryProperties {
        val memoryProperties = memScope.alloc<VkPhysicalDeviceMemoryProperties>()
        vkGetPhysicalDeviceMemoryProperties!!(handle, memoryProperties.ptr)
        return memoryProperties
    }

    /**
     * Retrieve properties of the physical device.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkGetPhysicalDeviceProperties.html">vkGetPhysicalDeviceProperties</a>
     */
    context(memScope: MemScope)
    fun getProperties(): VkPhysicalDeviceProperties {
        val properties = memScope.alloc<VkPhysicalDeviceProperties>()
        vkGetPhysicalDeviceProperties!!(handle, properties.ptr)
        return properties
    }

    /**
     * Retrieve properties of the physical device.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkGetPhysicalDeviceProperties2.html">vkGetPhysicalDeviceProperties2</a>
     */
    context(memScope: MemScope)
    fun getProperties2(): VkPhysicalDeviceProperties2 {
        val properties = memScope.alloc<VkPhysicalDeviceProperties2> {
            sType = VK_STRUCTURE_TYPE_PHYSICAL_DEVICE_PROPERTIES_2
        }
        vkGetPhysicalDeviceProperties2!!(handle, properties.ptr)
        return properties
    }

    /**
     * Retrieve properties of the queues available on the physical device.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkGetPhysicalDeviceQueueFamilyProperties.html">vkGetPhysicalDeviceQueueFamilyProperties</a>
     */
    context(memScope: MemScope)
    fun getQueueFamilyProperties(): Sequence<VkQueueFamilyProperties> {
        val countVar = memScope.alloc<UIntVar>()
        vkGetPhysicalDeviceQueueFamilyProperties!!(handle, countVar.ptr, null)

        val count = countVar.value.toInt()
        if (count == 0) return emptySequence()

        val queueFamilyProperties = memScope.allocArray<VkQueueFamilyProperties>(count)
        vkGetPhysicalDeviceQueueFamilyProperties!!(handle, countVar.ptr, queueFamilyProperties)

        return (0 until count).asSequence().map { queueFamilyProperties[it] }
    }

    /**
     * Query the basic capabilities of a surface.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkGetPhysicalDeviceSurfaceCapabilitiesKHR.html">vkGetPhysicalDeviceSurfaceCapabilitiesKHR</a>
     */
    context(memScope: MemScope)
    fun getSurfaceCapabilities(surface: Surface): VkSurfaceCapabilitiesKHR {
        val capabilities = memScope.alloc<VkSurfaceCapabilitiesKHR>()
        vkGetPhysicalDeviceSurfaceCapabilitiesKHR!!(handle, surface.handle, capabilities.ptr)
            .checkResult("Failed to get surface capabilities")
        return capabilities
    }

    /**
     * Query color formats supported by a surface.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkGetPhysicalDeviceSurfaceFormatsKHR.html">vkGetPhysicalDeviceSurfaceFormatsKHR</a>
     */
    context(memScope: MemScope)
    fun getSurfaceFormats(surface: Surface): Sequence<VkSurfaceFormatKHR> {
        val countVar = memScope.alloc<UIntVar>()
        vkGetPhysicalDeviceSurfaceFormatsKHR!!(handle, surface.handle, countVar.ptr, null)
            .checkResult("Failed to get surface formats")

        val count = countVar.value.toInt()
        if (count == 0) return emptySequence()

        val surfaceFormats = memScope.allocArray<VkSurfaceFormatKHR>(count)
        vkGetPhysicalDeviceSurfaceFormatsKHR!!(handle, surface.handle, countVar.ptr, surfaceFormats)
            .checkResult("Failed to get surface formats")

        return (0 until count).asSequence().map { surfaceFormats[it] }
    }

    /**
     * Query supported presentation modes for a surface.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkGetPhysicalDeviceSurfacePresentModesKHR.html">vkGetPhysicalDeviceSurfacePresentModesKHR</a>
     */
    context(memScope: MemScope)
    fun getSurfacePresentModes(surface: Surface): Set<VkPresentModeKHR> {
        val countVar = memScope.alloc<UIntVar>()
        vkGetPhysicalDeviceSurfacePresentModesKHR!!(handle, surface.handle, countVar.ptr, null)
            .checkResult("Failed to get surface present modes")

        val count = countVar.value.toInt()
        if (count == 0) return emptySet()

        val presentModes = memScope.allocArray<VkPresentModeKHRVar>(count)
        vkGetPhysicalDeviceSurfacePresentModesKHR!!(handle, surface.handle, countVar.ptr, presentModes)
            .checkResult("Failed to get surface present modes")

        return (0 until count).mapTo(mutableSetOf()) { presentModes[it] }
    }

    /**
     * Query if presentation is supported.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkGetPhysicalDeviceSurfaceSupportKHR.html">vkGetPhysicalDeviceSurfaceSupportKHR</a>
     */
    context(memScope: MemScope)
    fun getSurfaceSupport(surface: Surface, queueFamilyIndex: UInt): Boolean {
        val isSupported = memScope.alloc<VkBool32Var>()
        vkGetPhysicalDeviceSurfaceSupportKHR!!(handle, queueFamilyIndex, surface.handle, isSupported.ptr)
            .checkResult("Failed to check surface support")
        return isSupported.value == VK_TRUE
    }

    data class Features(
        val features: VkPhysicalDeviceFeatures2,
        val features13: VkPhysicalDeviceVulkan13Features,
        val features14: VkPhysicalDeviceVulkan14Features
    )
}
