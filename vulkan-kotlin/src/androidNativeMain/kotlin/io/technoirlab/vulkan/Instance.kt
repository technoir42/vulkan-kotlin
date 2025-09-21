package io.technoirlab.vulkan

import io.technoirlab.volk.VK_STRUCTURE_TYPE_ANDROID_SURFACE_CREATE_INFO_KHR
import io.technoirlab.volk.VkAndroidSurfaceCreateInfoKHR
import io.technoirlab.volk.VkSurfaceKHRVar
import io.technoirlab.volk.vkCreateAndroidSurfaceKHR
import kotlinx.cinterop.MemScope
import kotlinx.cinterop.alloc
import kotlinx.cinterop.invoke
import kotlinx.cinterop.ptr
import kotlinx.cinterop.value

/**
 * Create a surface for an Android native window.
 *
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCreateAndroidSurfaceKHR.html">vkCreateAndroidSurfaceKHR</a>
 */
context(memScope: MemScope)
fun Instance.createAndroidSurface(createInfo: VkAndroidSurfaceCreateInfoKHR.() -> Unit): Surface {
    val surfaceCreateInfo = memScope.alloc<VkAndroidSurfaceCreateInfoKHR> {
        sType = VK_STRUCTURE_TYPE_ANDROID_SURFACE_CREATE_INFO_KHR
        createInfo()
    }
    val surfaceVar = memScope.alloc<VkSurfaceKHRVar>()
    vkCreateAndroidSurfaceKHR!!(handle, surfaceCreateInfo.ptr, null, surfaceVar.ptr)
        .checkResult("Failed to create Android surface")
    return Surface(handle, surfaceVar.value!!)
}
