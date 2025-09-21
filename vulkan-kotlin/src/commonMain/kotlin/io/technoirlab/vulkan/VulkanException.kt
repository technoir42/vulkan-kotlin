package io.technoirlab.vulkan

import io.technoirlab.volk.VkResult
import io.technoirlab.volk.string_VkResult
import kotlinx.cinterop.toKString

class VulkanException(
    result: VkResult,
    message: String
) : Exception("$message: ${string_VkResult(result)?.toKString()}")
