package io.technoirlab.vulkan

import io.technoirlab.volk.VkInstance
import io.technoirlab.volk.VkSurfaceKHR
import io.technoirlab.volk.vkDestroySurfaceKHR
import kotlinx.cinterop.invoke

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
