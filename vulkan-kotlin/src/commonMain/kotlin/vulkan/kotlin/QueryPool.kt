package vulkan.kotlin

import kotlinx.cinterop.invoke
import volk.VkDevice
import volk.VkQueryPool
import volk.vkDestroyQueryPool

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
