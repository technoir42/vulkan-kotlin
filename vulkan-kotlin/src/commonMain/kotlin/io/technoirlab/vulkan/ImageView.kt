package io.technoirlab.vulkan

import io.technoirlab.volk.VkDevice
import io.technoirlab.volk.VkImageView
import io.technoirlab.volk.vkDestroyImageView
import kotlinx.cinterop.invoke

/**
 * Wrapper for [VkImageView].
 *
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/VkImageView.html">VkImageView</a>
 */
class ImageView(
    private val device: VkDevice,
    val handle: VkImageView
) : AutoCloseable {

    /**
     * Destroy the image view.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkDestroyImageView.html">vkDestroyImageView</a>
     */
    override fun close() {
        vkDestroyImageView!!(device, handle, null)
    }
}
