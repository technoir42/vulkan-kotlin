package io.technoirlab.vulkan

import io.technoirlab.volk.VK_STRUCTURE_TYPE_WIN32_SURFACE_CREATE_INFO_KHR
import io.technoirlab.volk.VkSurfaceKHRVar
import io.technoirlab.volk.VkWin32SurfaceCreateInfoKHR
import io.technoirlab.volk.vkCreateWin32SurfaceKHR
import kotlinx.cinterop.MemScope
import kotlinx.cinterop.alloc
import kotlinx.cinterop.invoke
import kotlinx.cinterop.ptr
import kotlinx.cinterop.value

/**
 * Create a surface for a Win32 native window.
 *
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCreateWin32SurfaceKHR.html">vkCreateWin32SurfaceKHR Manual Page</a>
 */
context(memScope: MemScope)
fun Instance.createWin32Surface(createInfo: VkWin32SurfaceCreateInfoKHR.() -> Unit): Surface {
    val surfaceCreateInfo = memScope.alloc<VkWin32SurfaceCreateInfoKHR> {
        sType = VK_STRUCTURE_TYPE_WIN32_SURFACE_CREATE_INFO_KHR
        createInfo()
    }
    val surfaceVar = memScope.alloc<VkSurfaceKHRVar>()
    vkCreateWin32SurfaceKHR!!(handle, surfaceCreateInfo.ptr, null, surfaceVar.ptr)
        .checkResult("Failed to create a Win32 surface")
    return Surface(handle, surfaceVar.value!!)
}
