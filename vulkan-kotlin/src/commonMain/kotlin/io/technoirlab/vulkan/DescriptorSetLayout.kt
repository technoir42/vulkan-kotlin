package io.technoirlab.vulkan

import io.technoirlab.volk.VkDescriptorSetLayout
import io.technoirlab.volk.VkDevice
import io.technoirlab.volk.vkDestroyDescriptorSetLayout
import kotlinx.cinterop.invoke

class DescriptorSetLayout(
    private val device: VkDevice,
    val handle: VkDescriptorSetLayout
) : AutoCloseable {

    /**
     * Destroy the descriptor set layout.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkDestroyDescriptorSetLayout.html">vkDestroyDescriptorSetLayout</a>
     */
    override fun close() {
        vkDestroyDescriptorSetLayout!!(device, handle, null)
    }
}
