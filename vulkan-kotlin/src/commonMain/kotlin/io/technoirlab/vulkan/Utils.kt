@file:Suppress("NOTHING_TO_INLINE")

package io.technoirlab.vulkan

import io.technoirlab.volk.VK_FALSE
import io.technoirlab.volk.VK_TRUE
import io.technoirlab.volk.VkBool32

internal inline fun Boolean.toVkBool32(): VkBool32 =
    if (this) VK_TRUE else VK_FALSE
