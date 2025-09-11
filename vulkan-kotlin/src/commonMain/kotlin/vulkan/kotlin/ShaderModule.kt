package vulkan.kotlin

import kotlinx.cinterop.invoke
import volk.VkDevice
import volk.VkShaderModule
import volk.vkDestroyShaderModule

class ShaderModule(
    private val device: VkDevice,
    val handle: VkShaderModule
) : AutoCloseable {

    /**
     * Destroy the shader module.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkDestroyShaderModule.html">vkDestroyShaderModule</a>
     */
    override fun close() {
        vkDestroyShaderModule!!(device, handle, null)
    }
}
