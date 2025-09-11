package vulkan.kotlin

import kotlinx.cinterop.MemScope
import kotlinx.cinterop.UIntVar
import kotlinx.cinterop.alloc
import kotlinx.cinterop.allocArray
import kotlinx.cinterop.get
import kotlinx.cinterop.invoke
import kotlinx.cinterop.ptr
import kotlinx.cinterop.value
import volk.VK_STRUCTURE_TYPE_DEBUG_UTILS_MESSENGER_CREATE_INFO_EXT
import volk.VkDebugUtilsMessengerCreateInfoEXT
import volk.VkDebugUtilsMessengerEXTVar
import volk.VkInstance
import volk.VkPhysicalDeviceVar
import volk.vkCreateDebugUtilsMessengerEXT
import volk.vkDestroyInstance
import volk.vkEnumeratePhysicalDevices
import volk.volkLoadInstanceOnly

class Instance(val handle: VkInstance) : AutoCloseable {
    init {
        volkLoadInstanceOnly(handle)
    }

    /**
     * Create a debug messenger.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCreateDebugUtilsMessengerEXT.html">vkCreateDebugUtilsMessengerEXT</a>
     */
    context(memScope: MemScope)
    fun createDebugUtilsMessenger(createInfo: VkDebugUtilsMessengerCreateInfoEXT.() -> Unit): DebugUtilsMessenger {
        val createInfo = memScope.alloc<VkDebugUtilsMessengerCreateInfoEXT> {
            sType = VK_STRUCTURE_TYPE_DEBUG_UTILS_MESSENGER_CREATE_INFO_EXT
            createInfo()
        }
        val messengerVar = memScope.alloc<VkDebugUtilsMessengerEXTVar>()
        vkCreateDebugUtilsMessengerEXT!!(handle, createInfo.ptr, null, messengerVar.ptr)
            .checkResult("Failed to create a debug utils messenger")
        return DebugUtilsMessenger(handle, messengerVar.value!!)
    }

    /**
     * Enumerates the physical devices accessible to a Vulkan instance.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkEnumeratePhysicalDevices.html">vkEnumeratePhysicalDevices</a>
     */
    context(memScope: MemScope)
    fun enumeratePhysicalDevices(): Sequence<PhysicalDevice> {
        val countVar = memScope.alloc<UIntVar>()
        vkEnumeratePhysicalDevices!!(handle, countVar.ptr, null)
            .checkResult("Failed to enumerate physical devices")

        val count = countVar.value.toInt()
        if (count == 0) return emptySequence()

        val physicalDevices = memScope.allocArray<VkPhysicalDeviceVar>(count)
        vkEnumeratePhysicalDevices!!(handle, countVar.ptr, physicalDevices)
            .checkResult("Failed to enumerate physical devices")

        return (0 until count).asSequence().map { PhysicalDevice(physicalDevices[it]!!) }
    }

    /**
     * Destroy the instance of Vulkan.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkDestroyInstance.html">vkDestroyInstance</a>
     */
    override fun close() {
        vkDestroyInstance!!(handle, null)
    }
}
