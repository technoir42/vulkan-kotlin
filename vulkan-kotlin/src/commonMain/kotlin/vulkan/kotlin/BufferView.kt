package vulkan.kotlin

import kotlinx.cinterop.invoke
import volk.VkBufferView
import volk.VkDevice
import volk.vkDestroyBufferView

class BufferView(
    private val device: VkDevice,
    val handle: VkBufferView
) : AutoCloseable {

    /**
     * Destroy the buffer view.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkDestroyBufferView.html">vkDestroyBufferView</a>
     */
    override fun close() {
        vkDestroyBufferView!!(device, handle, null)
    }
}
