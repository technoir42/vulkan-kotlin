package vulkan.kotlin

import kotlinx.cinterop.invoke
import volk.VkDevice
import volk.VkImageView
import volk.vkDestroyImageView

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
