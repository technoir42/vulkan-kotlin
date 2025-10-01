package io.technoirlab.vulkan

import io.technoirlab.volk.VkDevice
import io.technoirlab.volk.VkPipeline
import io.technoirlab.volk.vkDestroyPipeline
import kotlinx.cinterop.invoke

/**
 * Wrapper for [VkPipeline].
 *
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/VkPipeline.html">VkPipeline Manual Page</a>
 */
class Pipeline(
    private val device: VkDevice,
    val handle: VkPipeline
) : AutoCloseable {

    /**
     * Destroy the pipeline.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkDestroyPipeline.html">vkDestroyPipeline Manual Page</a>
     */
    override fun close() {
        vkDestroyPipeline!!(device, handle, null)
    }
}
