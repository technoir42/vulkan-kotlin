@file:Suppress("NOTHING_TO_INLINE")

package vulkan.kotlin

import volk.VK_FALSE
import volk.VK_TRUE
import volk.VkBool32

internal inline fun Boolean.toVkBool32(): VkBool32 =
    if (this) VK_TRUE else VK_FALSE
