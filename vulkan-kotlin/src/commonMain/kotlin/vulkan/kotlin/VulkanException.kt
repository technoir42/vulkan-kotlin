package vulkan.kotlin

import kotlinx.cinterop.toKString
import volk.VkResult
import volk.string_VkResult

class VulkanException(
    result: VkResult,
    message: String
) : Exception("$message: ${string_VkResult(result)?.toKString()}")
