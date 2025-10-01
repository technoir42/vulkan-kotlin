package io.technoirlab.vulkan

import io.technoirlab.volk.VkInstance
import io.technoirlab.volk.VkSurfaceKHR
import io.technoirlab.volk.vkDestroySurfaceKHR
import kotlinx.cinterop.invoke

/**
 * Wrapper for [VkSurfaceKHR].
 *
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/VkSurfaceKHR.html">VkSurfaceKHR Manual Page</a>
 */
class Surface(
    private val instance: VkInstance,
    val handle: VkSurfaceKHR
) : AutoCloseable {

    /**
     * Destroy the surface.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkDestroySurfaceKHR.html">vkDestroySurfaceKHR Manual Page</a>
     */
    override fun close() {
        vkDestroySurfaceKHR!!(instance, handle, null)
    }
}
