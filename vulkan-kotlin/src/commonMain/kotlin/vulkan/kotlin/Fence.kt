package vulkan.kotlin

import kotlinx.cinterop.MemScope
import kotlinx.cinterop.alloc
import kotlinx.cinterop.invoke
import kotlinx.cinterop.ptr
import kotlinx.cinterop.value
import volk.VK_TRUE
import volk.VkDevice
import volk.VkFence
import volk.VkFenceVar
import volk.vkDestroyFence
import volk.vkResetFences
import volk.vkWaitForFences

class Fence(
    private val device: VkDevice,
    val handle: VkFence
) : AutoCloseable {

    /**
     * Destroy the fence.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkDestroyFence.html">vkDestroyFence</a>
     */
    override fun close() {
        vkDestroyFence!!(device, handle, null)
    }

    /**
     * Resets the status of the fence from signaled to unsignaled state.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkResetFences.html">vkResetFences</a>
     */
    context(memScope: MemScope)
    fun reset() {
        val fenceVar = memScope.alloc<VkFenceVar> {
            value = handle
        }
        vkResetFences!!(device, 1u, fenceVar.ptr)
            .checkResult("Failed to reset fence")
    }

    /**
     * Wait for the fence to become signaled.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkWaitForFences.html">vkWaitForFences</a>
     */
    context(memScope: MemScope)
    fun wait(timeout: ULong = ULong.MAX_VALUE) {
        val fenceVar = memScope.alloc<VkFenceVar> {
            value = handle
        }
        vkWaitForFences!!(device, 1u, fenceVar.ptr, VK_TRUE, timeout)
            .checkResult("Failed to wait for fence")
    }
}
