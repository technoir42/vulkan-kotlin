package io.technoirlab.vulkan

import io.technoirlab.volk.VK_STRUCTURE_TYPE_BIND_IMAGE_MEMORY_INFO
import io.technoirlab.volk.VK_STRUCTURE_TYPE_IMAGE_MEMORY_REQUIREMENTS_INFO_2
import io.technoirlab.volk.VK_STRUCTURE_TYPE_MEMORY_REQUIREMENTS_2
import io.technoirlab.volk.VkBindImageMemoryInfo
import io.technoirlab.volk.VkDevice
import io.technoirlab.volk.VkImage
import io.technoirlab.volk.VkImageMemoryRequirementsInfo2
import io.technoirlab.volk.VkMemoryRequirements
import io.technoirlab.volk.VkMemoryRequirements2
import io.technoirlab.volk.vkBindImageMemory2
import io.technoirlab.volk.vkDestroyImage
import io.technoirlab.volk.vkGetImageMemoryRequirements2
import kotlinx.cinterop.MemScope
import kotlinx.cinterop.alloc
import kotlinx.cinterop.invoke
import kotlinx.cinterop.ptr

/**
 * Wrapper for [VkImage].
 *
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/VkImage.html">VkImage Manual Page</a>
 */
class Image(
    private val device: VkDevice,
    val handle: VkImage,
    val destroyable: Boolean = true
) : AutoCloseable {

    /**
     * Returns memory requirements for the image.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkGetImageMemoryRequirements2.html">vkGetImageMemoryRequirements2 Manual Page</a>
     */
    context(memScope: MemScope)
    fun getMemoryRequirements(): VkMemoryRequirements {
        val memoryRequirementsInfo = memScope.alloc<VkImageMemoryRequirementsInfo2> {
            sType = VK_STRUCTURE_TYPE_IMAGE_MEMORY_REQUIREMENTS_INFO_2
            image = handle
        }
        val memoryRequirements = memScope.alloc<VkMemoryRequirements2> {
            sType = VK_STRUCTURE_TYPE_MEMORY_REQUIREMENTS_2
        }
        vkGetImageMemoryRequirements2!!(device, memoryRequirementsInfo.ptr, memoryRequirements.ptr)
        return memoryRequirements.memoryRequirements
    }

    /**
     * Bind device memory to the image.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkBindImageMemory2.html">vkBindImageMemory2 Manual Page</a>
     */
    context(memScope: MemScope)
    fun bindMemory(memory: DeviceMemory, offset: ULong = 0u) {
        val bindImageMemoryInfo = memScope.alloc<VkBindImageMemoryInfo> {
            sType = VK_STRUCTURE_TYPE_BIND_IMAGE_MEMORY_INFO
            image = handle
            memoryOffset = offset
            this.memory = memory.handle
        }
        vkBindImageMemory2!!(device, 1u, bindImageMemoryInfo.ptr)
            .checkResult("Failed to bind image memory")
    }

    /**
     * Destroy the image.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkDestroyImage.html">vkDestroyImage Manual Page</a>
     */
    override fun close() {
        if (destroyable) {
            vkDestroyImage!!(device, handle, null)
        }
    }
}
