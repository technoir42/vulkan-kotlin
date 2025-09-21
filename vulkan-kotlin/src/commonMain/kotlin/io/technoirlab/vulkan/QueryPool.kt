package io.technoirlab.vulkan

import io.technoirlab.volk.VkDevice
import io.technoirlab.volk.VkQueryPool
import io.technoirlab.volk.vkDestroyQueryPool
import kotlinx.cinterop.invoke

class QueryPool(
    private val device: VkDevice,
    val handle: VkQueryPool
) : AutoCloseable {

    /**
     * Destroy the query pool.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkDestroyQueryPool.html">vkDestroyQueryPool</a>
     */
    override fun close() {
        vkDestroyQueryPool!!(device, handle, null)
    }
}
