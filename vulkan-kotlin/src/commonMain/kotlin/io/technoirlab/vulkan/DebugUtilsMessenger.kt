package io.technoirlab.vulkan

import io.technoirlab.volk.VkDebugUtilsMessengerEXT
import io.technoirlab.volk.VkInstance
import io.technoirlab.volk.vkDestroyDebugUtilsMessengerEXT
import kotlinx.cinterop.invoke

/**
 * Wrapper for [VkDebugUtilsMessengerEXT].
 *
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/VkDebugUtilsMessengerEXT.html">VkDebugUtilsMessengerEXT</a>
 */
class DebugUtilsMessenger(
    private val instance: VkInstance,
    val handle: VkDebugUtilsMessengerEXT
) : AutoCloseable {

    /**
     * Destroy the debug messenger.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkDestroyDebugUtilsMessengerEXT.html">vkDestroyDebugUtilsMessengerEXT</a>
     */
    override fun close() {
        vkDestroyDebugUtilsMessengerEXT!!(instance, handle, null)
    }
}
