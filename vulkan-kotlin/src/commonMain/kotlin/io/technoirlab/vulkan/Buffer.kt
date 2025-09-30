package io.technoirlab.vulkan

import io.technoirlab.volk.VK_STRUCTURE_TYPE_BIND_BUFFER_MEMORY_INFO
import io.technoirlab.volk.VK_STRUCTURE_TYPE_BUFFER_MEMORY_REQUIREMENTS_INFO_2
import io.technoirlab.volk.VK_STRUCTURE_TYPE_MEMORY_REQUIREMENTS_2
import io.technoirlab.volk.VkBindBufferMemoryInfo
import io.technoirlab.volk.VkBuffer
import io.technoirlab.volk.VkBufferMemoryRequirementsInfo2
import io.technoirlab.volk.VkDevice
import io.technoirlab.volk.VkMemoryRequirements
import io.technoirlab.volk.VkMemoryRequirements2
import io.technoirlab.volk.vkBindBufferMemory2
import io.technoirlab.volk.vkDestroyBuffer
import io.technoirlab.volk.vkGetBufferMemoryRequirements2
import kotlinx.cinterop.MemScope
import kotlinx.cinterop.alloc
import kotlinx.cinterop.invoke
import kotlinx.cinterop.ptr

/**
 * Wrapper for [VkBuffer].
 *
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/VkBuffer.html">VkBuffer</a>
 */
class Buffer(
    private val device: VkDevice,
    val handle: VkBuffer,
    val size: ULong
) : AutoCloseable {

    /**
     * Returns memory requirements for the buffer.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkGetBufferMemoryRequirements2.html">vkGetBufferMemoryRequirements2</a>
     */
    context(memScope: MemScope)
    fun getMemoryRequirements(): VkMemoryRequirements {
        val memoryRequirementsInfo = memScope.alloc<VkBufferMemoryRequirementsInfo2> {
            sType = VK_STRUCTURE_TYPE_BUFFER_MEMORY_REQUIREMENTS_INFO_2
            buffer = handle
        }
        val memoryRequirements = memScope.alloc<VkMemoryRequirements2> {
            sType = VK_STRUCTURE_TYPE_MEMORY_REQUIREMENTS_2
        }
        vkGetBufferMemoryRequirements2!!(device, memoryRequirementsInfo.ptr, memoryRequirements.ptr)
        return memoryRequirements.memoryRequirements
    }

    /**
     * Bind device memory to the buffer.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkBindBufferMemory2.html">vkBindBufferMemory2</a>
     */
    context(memScope: MemScope)
    fun bindMemory(memory: DeviceMemory, offset: ULong = 0u) {
        val bindImageMemoryInfo = memScope.alloc<VkBindBufferMemoryInfo> {
            sType = VK_STRUCTURE_TYPE_BIND_BUFFER_MEMORY_INFO
            buffer = handle
            memoryOffset = offset
            this.memory = memory.handle
        }
        vkBindBufferMemory2!!(device, 1u, bindImageMemoryInfo.ptr)
            .checkResult("Failed to bind buffer memory")
    }

    /**
     * Destroy the buffer.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkDestroyBuffer.html">vkDestroyBuffer</a>
     */
    override fun close() {
        vkDestroyBuffer!!(device, handle, null)
    }
}
