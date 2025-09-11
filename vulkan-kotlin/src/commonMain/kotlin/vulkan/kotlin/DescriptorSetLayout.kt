package vulkan.kotlin

import kotlinx.cinterop.invoke
import volk.VkDescriptorSetLayout
import volk.VkDevice
import volk.vkDestroyDescriptorSetLayout

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
