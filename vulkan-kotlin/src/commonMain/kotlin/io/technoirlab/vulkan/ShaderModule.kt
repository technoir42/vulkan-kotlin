package io.technoirlab.vulkan

import io.technoirlab.volk.VkDevice
import io.technoirlab.volk.VkShaderModule
import io.technoirlab.volk.vkDestroyShaderModule
import kotlinx.cinterop.invoke

/**
 * Wrapper for [VkShaderModule].
 *
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/VkShaderModule.html">VkShaderModule Manual Page</a>
 */
class ShaderModule(
    private val device: VkDevice,
    val handle: VkShaderModule
) : AutoCloseable {

    /**
     * Destroy the shader module.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkDestroyShaderModule.html">vkDestroyShaderModule Manual Page</a>
     */
    override fun close() {
        vkDestroyShaderModule!!(device, handle, null)
    }
}
