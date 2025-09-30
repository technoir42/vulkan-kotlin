package io.technoirlab.vulkan

import io.technoirlab.volk.VkBufferView
import io.technoirlab.volk.VkDevice
import io.technoirlab.volk.vkDestroyBufferView
import kotlinx.cinterop.invoke

/**
 * Wrapper for [VkBufferView].
 *
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/VkBufferView.html">VkBufferView</a>
 */
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
