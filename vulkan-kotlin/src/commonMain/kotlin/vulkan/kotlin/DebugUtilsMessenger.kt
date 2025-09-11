package vulkan.kotlin

import kotlinx.cinterop.invoke
import volk.VkDebugUtilsMessengerEXT
import volk.VkInstance
import volk.vkDestroyDebugUtilsMessengerEXT

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
