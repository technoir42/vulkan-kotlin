package vulkan.kotlin

import kotlinx.cinterop.invoke
import volk.VkDevice
import volk.VkSampler
import volk.vkDestroySampler

class Sampler(
    private val device: VkDevice,
    val handle: VkSampler
) : AutoCloseable {

    /**
     * Destroy the sampler.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkDestroySampler.html">vkDestroySampler</a>
     */
    override fun close() {
        vkDestroySampler!!(device, handle, null)
    }
}
