package io.technoirlab.vulkan

import io.technoirlab.volk.VkDevice
import io.technoirlab.volk.VkPipelineCache
import io.technoirlab.volk.vkDestroyPipelineCache
import io.technoirlab.volk.vkMergePipelineCaches
import kotlinx.cinterop.MemScope
import kotlinx.cinterop.allocArrayOf
import kotlinx.cinterop.invoke

/**
 * Wrapper for [VkPipelineCache].
 *
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/VkPipelineCache.html">VkPipelineCache Manual Page</a>
 */
class PipelineCache(
    private val device: VkDevice,
    val handle: VkPipelineCache
) : AutoCloseable {

    /**
     * Combine the data stores of pipeline caches.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkMergePipelineCaches.html">vkMergePipelineCaches Manual Page</a>
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
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkDestroyPipelineCache.html">vkDestroyPipelineCache Manual Page</a>
     */
    override fun close() {
        vkDestroyPipelineCache!!(device, handle, null)
    }
}
