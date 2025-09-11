package vulkan.kotlin

import kotlinx.cinterop.invoke
import volk.VkDevice
import volk.VkPipeline
import volk.vkDestroyPipeline

class Pipeline(
    private val device: VkDevice,
    val handle: VkPipeline
) : AutoCloseable {

    /**
     * Destroy the pipeline.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkDestroyPipeline.html">vkDestroyPipeline</a>
     */
    override fun close() {
        vkDestroyPipeline!!(device, handle, null)
    }
}
