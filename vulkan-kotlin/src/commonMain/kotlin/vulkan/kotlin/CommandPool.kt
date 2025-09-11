package vulkan.kotlin

import kotlinx.cinterop.MemScope
import kotlinx.cinterop.alloc
import kotlinx.cinterop.allocArray
import kotlinx.cinterop.get
import kotlinx.cinterop.invoke
import kotlinx.cinterop.ptr
import kotlinx.cinterop.value
import volk.VK_COMMAND_BUFFER_LEVEL_PRIMARY
import volk.VK_STRUCTURE_TYPE_COMMAND_BUFFER_ALLOCATE_INFO
import volk.VkCommandBufferAllocateInfo
import volk.VkCommandBufferVar
import volk.VkCommandPool
import volk.VkDevice
import volk.vkAllocateCommandBuffers
import volk.vkDestroyCommandPool
import volk.vkFreeCommandBuffers

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
        val commandBufferArray = memScope.allocArray<VkCommandBufferVar>(commandBuffers.size) {
            value = commandBuffers[it].handle
        }
        vkFreeCommandBuffers!!(device, handle, commandBuffers.size.toUInt(), commandBufferArray)
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
