package io.technoirlab.vulkan

import io.technoirlab.volk.VkDevice
import io.technoirlab.volk.VkPipelineLayout
import io.technoirlab.volk.vkDestroyPipelineLayout
import kotlinx.cinterop.invoke

/**
 * Wrapper for [VkPipelineLayout].
 *
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/VkPipelineLayout.html">VkPipelineLayout Manual Page</a>
 */
class PipelineLayout(
    private val device: VkDevice,
    val handle: VkPipelineLayout
) : AutoCloseable {

    /**
     * Destroy the pipeline layout.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkDestroyPipelineLayout.html">vkDestroyPipelineLayout Manual Page</a>
     */
    override fun close() {
        vkDestroyPipelineLayout!!(device, handle, null)
    }
}
