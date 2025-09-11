package vulkan.kotlin

import kotlinx.cinterop.invoke
import volk.VkDevice
import volk.VkPipelineLayout
import volk.vkDestroyPipelineLayout

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
