package io.technoirlab.vulkan

import io.technoirlab.volk.VkDevice
import io.technoirlab.volk.VkPipelineLayout
import io.technoirlab.volk.vkDestroyPipelineLayout
import kotlinx.cinterop.invoke

class PipelineLayout(
    private val device: VkDevice,
    val handle: VkPipelineLayout
) : AutoCloseable {

    /**
     * Destroy the pipeline layout.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkDestroyPipelineLayout.html">vkDestroyPipelineLayout</a>
     */
    override fun close() {
        vkDestroyPipelineLayout!!(device, handle, null)
    }
}
