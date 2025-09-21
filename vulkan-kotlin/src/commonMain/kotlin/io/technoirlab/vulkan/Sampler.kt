package io.technoirlab.vulkan

import io.technoirlab.volk.VkDevice
import io.technoirlab.volk.VkSampler
import io.technoirlab.volk.vkDestroySampler
import kotlinx.cinterop.invoke

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
