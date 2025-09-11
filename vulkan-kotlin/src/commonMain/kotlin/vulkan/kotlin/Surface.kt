package vulkan.kotlin

import kotlinx.cinterop.invoke
import volk.VkInstance
import volk.VkSurfaceKHR
import volk.vkDestroySurfaceKHR

class Surface(
    private val instance: VkInstance,
    val handle: VkSurfaceKHR
) : AutoCloseable {

    /**
     * Destroy the surface.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkDestroySurfaceKHR.html">vkDestroySurfaceKHR</a>
     */
    override fun close() {
        vkDestroySurfaceKHR!!(instance, handle, null)
    }
}
