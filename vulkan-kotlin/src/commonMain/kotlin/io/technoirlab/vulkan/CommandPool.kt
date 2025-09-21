package io.technoirlab.vulkan

import io.technoirlab.volk.VK_COMMAND_BUFFER_LEVEL_PRIMARY
import io.technoirlab.volk.VK_STRUCTURE_TYPE_COMMAND_BUFFER_ALLOCATE_INFO
import io.technoirlab.volk.VkCommandBufferAllocateInfo
import io.technoirlab.volk.VkCommandBufferVar
import io.technoirlab.volk.VkCommandPool
import io.technoirlab.volk.VkDevice
import io.technoirlab.volk.vkAllocateCommandBuffers
import io.technoirlab.volk.vkDestroyCommandPool
import io.technoirlab.volk.vkFreeCommandBuffers
import io.technoirlab.volk.vkResetCommandPool
import io.technoirlab.volk.vkTrimCommandPool
import kotlinx.cinterop.MemScope
import kotlinx.cinterop.alloc
import kotlinx.cinterop.allocArray
import kotlinx.cinterop.allocArrayOf
import kotlinx.cinterop.get
import kotlinx.cinterop.invoke
import kotlinx.cinterop.ptr

class CommandPool(
    private val device: VkDevice,
    val handle: VkCommandPool
) : AutoCloseable {

    /**
     * Allocate command buffers from the command pool.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkAllocateCommandBuffers.html">vkAllocateCommandBuffers</a>
     */
    context(memScope: MemScope)
    fun allocateCommandBuffers(count: Int): List<CommandBuffer> {
        val allocateInfo = memScope.alloc<VkCommandBufferAllocateInfo> {
            sType = VK_STRUCTURE_TYPE_COMMAND_BUFFER_ALLOCATE_INFO
            commandPool = handle
            commandBufferCount = count.toUInt()
            level = VK_COMMAND_BUFFER_LEVEL_PRIMARY
        }
        val commandBufferArray = memScope.allocArray<VkCommandBufferVar>(count)
        vkAllocateCommandBuffers!!(device, allocateInfo.ptr, commandBufferArray)
            .checkResult("Failed to allocate command buffers")
        return (0 until count).map { CommandBuffer(commandBufferArray[it]!!) }
    }

    /**
     * Free command buffers.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkFreeCommandBuffers.html">vkFreeCommandBuffers</a>
     */
    context(memScope: MemScope)
    fun freeCommandBuffers(commandBuffers: List<CommandBuffer>) {
        val commandBufferHandles = memScope.allocArrayOf(commandBuffers.map { it.handle })
        vkFreeCommandBuffers!!(device, handle, commandBuffers.size.toUInt(), commandBufferHandles)
    }

    /**
     * Reset the command pool, releasing resources from all command buffers allocated from it.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkResetCommandPool.html">vkResetCommandPool</a>
     */
    fun reset(flags: UInt = 0u) {
        vkResetCommandPool!!(device, handle, flags)
            .checkResult("Failed to reset command pool")
    }

    /**
     * Trim internal memory allocations owned by the command pool.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkTrimCommandPool.html">vkTrimCommandPool</a>
     */
    fun trim(flags: UInt = 0u) {
        vkTrimCommandPool!!(device, handle, flags)
    }

    /**
     * Destroy the command pool.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkDestroyCommandPool.html">vkDestroyCommandPool</a>
     */
    override fun close() {
        vkDestroyCommandPool!!(device, handle, null)
    }
}
