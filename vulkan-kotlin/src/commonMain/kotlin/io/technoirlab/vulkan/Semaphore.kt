package io.technoirlab.vulkan

import io.technoirlab.volk.VK_STRUCTURE_TYPE_SEMAPHORE_SIGNAL_INFO
import io.technoirlab.volk.VkDevice
import io.technoirlab.volk.VkSemaphore
import io.technoirlab.volk.VkSemaphoreSignalInfo
import io.technoirlab.volk.vkDestroySemaphore
import io.technoirlab.volk.vkSignalSemaphore
import kotlinx.cinterop.MemScope
import kotlinx.cinterop.alloc
import kotlinx.cinterop.invoke
import kotlinx.cinterop.ptr

class Semaphore(
    private val device: VkDevice,
    val handle: VkSemaphore
) : AutoCloseable {

    /**
     * Signal the timeline semaphore on the host.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkSignalSemaphore.html">vkSignalSemaphore</a>
     */
    context(memScope: MemScope)
    fun signal(value: ULong) {
        val signalInfo = memScope.alloc<VkSemaphoreSignalInfo> {
            sType = VK_STRUCTURE_TYPE_SEMAPHORE_SIGNAL_INFO
            semaphore = handle
            this.value = value
        }
        vkSignalSemaphore!!(device, signalInfo.ptr)
            .checkResult("Failed to signal semaphore")
    }

    /**
     * Destroy the semaphore.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkDestroySemaphore.html">vkDestroySemaphore</a>
     */
    override fun close() {
        vkDestroySemaphore!!(device, handle, null)
    }
}
