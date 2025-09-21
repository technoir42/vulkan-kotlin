package io.technoirlab.vulkan

import io.technoirlab.volk.VkDevice
import io.technoirlab.volk.VkShaderModule
import io.technoirlab.volk.vkDestroyShaderModule
import kotlinx.cinterop.invoke

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
