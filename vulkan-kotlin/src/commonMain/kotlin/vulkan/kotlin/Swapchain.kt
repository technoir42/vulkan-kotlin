package vulkan.kotlin

import kotlinx.cinterop.MemScope
import kotlinx.cinterop.UIntVar
import kotlinx.cinterop.alloc
import kotlinx.cinterop.allocArray
import kotlinx.cinterop.get
import kotlinx.cinterop.invoke
import kotlinx.cinterop.ptr
import kotlinx.cinterop.value
import volk.VK_ERROR_OUT_OF_DATE_KHR
import volk.VK_STRUCTURE_TYPE_ACQUIRE_NEXT_IMAGE_INFO_KHR
import volk.VK_SUBOPTIMAL_KHR
import volk.VkAcquireNextImageInfoKHR
import volk.VkDevice
import volk.VkImageVar
import volk.VkSwapchainKHR
import volk.vkAcquireNextImage2KHR
import volk.vkDestroySwapchainKHR
import volk.vkGetSwapchainImagesKHR
import kotlin.time.Duration

class Swapchain(
    private val device: VkDevice,
    val handle: VkSwapchainKHR
) : AutoCloseable {

    /**
     * Retrieve the index of the next available presentable image.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkAcquireNextImage2KHR.html">vkAcquireNextImage2KHR</a>
     */
    context(memScope: MemScope)
    fun acquireNextImage(
        semaphore: Semaphore? = null,
        fence: Fence? = null,
        timeout: Duration = Duration.INFINITE
    ): Result<UInt> {
        val acquireInfo = memScope.alloc<VkAcquireNextImageInfoKHR> {
            sType = VK_STRUCTURE_TYPE_ACQUIRE_NEXT_IMAGE_INFO_KHR
            deviceMask = 1u
            swapchain = handle
            this.semaphore = semaphore?.handle
            this.fence = fence?.handle
            this.timeout = timeout.inWholeNanoseconds.toULong()
        }
        val imageIndexVar = memScope.alloc<UIntVar>()
        val result = vkAcquireNextImage2KHR!!(device, acquireInfo.ptr, imageIndexVar.ptr)
        if (result != VK_ERROR_OUT_OF_DATE_KHR && result != VK_SUBOPTIMAL_KHR) {
            result.checkResult("Failed to acquire next swap chain image")
        }
        return Result(imageIndexVar.value, result)
    }

    /**
     * Obtain the array of presentable images associated with the swapchain.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkGetSwapchainImagesKHR.html">vkGetSwapchainImagesKHR</a>
     */
    context(memScope: MemScope)
    fun getImages(): List<Image> {
        val countVar = memScope.alloc<UIntVar>()
        vkGetSwapchainImagesKHR!!(device, handle, countVar.ptr, null)
            .checkResult("Failed to get swap chain image count")

        val count = countVar.value.toInt()
        if (count == 0) return emptyList()

        val images = memScope.allocArray<VkImageVar>(count)
        vkGetSwapchainImagesKHR!!(device, handle, countVar.ptr, images)
            .checkResult("Failed to get swap chain images")

        return (0 until count).map { Image(device, images[it]!!, destroyable = false) }
    }

    /**
     * Destroy the swapchain.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkDestroySwapchainKHR.html">vkDestroySwapchainKHR</a>
     */
    override fun close() {
        vkDestroySwapchainKHR!!(device, handle, null)
    }
}
