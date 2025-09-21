package io.technoirlab.vulkan

import io.technoirlab.volk.VK_SUCCESS
import io.technoirlab.volk.VkResult

data class Result<T>(
    val payload: T,
    val status: VkResult
)

@Suppress("NOTHING_TO_INLINE")
inline fun VkResult.checkResult(message: String) {
    if (this != VK_SUCCESS) throw VulkanException(this, message)
}
