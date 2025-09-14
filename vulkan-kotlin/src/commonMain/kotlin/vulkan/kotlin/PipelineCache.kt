package vulkan.kotlin

import kotlinx.cinterop.MemScope
import kotlinx.cinterop.allocArrayOf
import kotlinx.cinterop.invoke
import volk.VkDevice
import volk.VkPipelineCache
import volk.vkDestroyPipelineCache
import volk.vkMergePipelineCaches

class PipelineCache(
    private val device: VkDevice,
    val handle: VkPipelineCache
) : AutoCloseable {

    /**
     * Combine the data stores of pipeline caches.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkMergePipelineCaches.html">vkMergePipelineCaches</a>
     */
    context(memScope: MemScope)
    fun merge(srcCaches: List<PipelineCache>) {
        val srcCacheHandles = memScope.allocArrayOf(srcCaches.map { it.handle })
        vkMergePipelineCaches!!(device, handle, srcCaches.size.toUInt(), srcCacheHandles)
            .checkResult("Failed to merge pipeline caches")
    }

    /**
     * Destroy the pipeline cache.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkDestroyPipelineCache.html">vkDestroyPipelineCache</a>
     */
    override fun close() {
        vkDestroyPipelineCache!!(device, handle, null)
    }
}
