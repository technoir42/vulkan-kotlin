package vulkan.kotlin

import kotlinx.cinterop.MemScope
import kotlinx.cinterop.UIntVar
import kotlinx.cinterop.alloc
import kotlinx.cinterop.allocArrayOf
import kotlinx.cinterop.invoke
import kotlinx.cinterop.ptr
import kotlinx.cinterop.value
import volk.VK_ERROR_OUT_OF_DATE_KHR
import volk.VK_STRUCTURE_TYPE_PRESENT_INFO_KHR
import volk.VK_STRUCTURE_TYPE_SUBMIT_INFO_2
import volk.VK_SUBOPTIMAL_KHR
import volk.VkPresentInfoKHR
import volk.VkQueue
import volk.VkResult
import volk.VkSubmitInfo2
import volk.VkSwapchainKHRVar
import volk.vkQueuePresentKHR
import volk.vkQueueSubmit2
import volk.vkQueueWaitIdle

class Queue(
    val handle: VkQueue,
    val familyIndex: UInt
) {
    /**
     * Queue an image for presentation.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkQueuePresentKHR.html">vkQueuePresentKHR</a>
     */
    context(memScope: MemScope)
    fun present(swapChain: Swapchain, imageIndex: UInt, waitSemaphores: List<Semaphore> = emptyList()): VkResult {
        val swapChainVar = memScope.alloc<VkSwapchainKHRVar> {
            value = swapChain.handle
        }
        val imageIndexVar = memScope.alloc<UIntVar> {
            value = imageIndex
        }
        val presentInfo = memScope.alloc<VkPresentInfoKHR> {
            sType = VK_STRUCTURE_TYPE_PRESENT_INFO_KHR
            swapchainCount = 1u
            pSwapchains = swapChainVar.ptr
            pImageIndices = imageIndexVar.ptr
            if (waitSemaphores.isNotEmpty()) {
                waitSemaphoreCount = waitSemaphores.size.toUInt()
                pWaitSemaphores = memScope.allocArrayOf(waitSemaphores.map { it.handle })
            }
        }
        val result = vkQueuePresentKHR!!(handle, presentInfo.ptr)
        if (result != VK_ERROR_OUT_OF_DATE_KHR && result != VK_SUBOPTIMAL_KHR) {
            result.checkResult("Queue present failed")
        }
        return result
    }

    /**
     * Submits a sequence of semaphores or command buffers to the queue.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkQueueSubmit2.html">vkQueueSubmit2</a>
     */
    context(memScope: MemScope)
    fun submit(fence: Fence? = null, submitInfo: VkSubmitInfo2.() -> Unit) {
        val submitInfo = memScope.alloc<VkSubmitInfo2> {
            sType = VK_STRUCTURE_TYPE_SUBMIT_INFO_2
            submitInfo()
        }
        vkQueueSubmit2!!(handle, 1u, submitInfo.ptr, fence?.handle)
            .checkResult("Queue submit failed")
    }

    /**
     * Wait for the queue to become idle.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkQueueWaitIdle.html">vkQueueWaitIdle</a>
     */
    fun waitIdle() {
        vkQueueWaitIdle!!(handle).checkResult("Failed to wait for queue idle")
    }
}
