package vulkan.kotlin

import kotlinx.cinterop.MemScope
import kotlinx.cinterop.alloc
import kotlinx.cinterop.invoke
import kotlinx.cinterop.ptr
import kotlinx.cinterop.value
import volk.VK_STRUCTURE_TYPE_WAYLAND_SURFACE_CREATE_INFO_KHR
import volk.VkSurfaceKHRVar
import volk.VkWaylandSurfaceCreateInfoKHR
import volk.vkCreateWaylandSurfaceKHR

/**
 * Create a surface for a Wayland window.
 *
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCreateWaylandSurfaceKHR.html">vkCreateWaylandSurfaceKHR</a>
 */
context(memScope: MemScope)
fun Instance.createWaylandSurface(createInfo: VkWaylandSurfaceCreateInfoKHR.() -> Unit): Surface {
    val surfaceCreateInfo = memScope.alloc<VkWaylandSurfaceCreateInfoKHR> {
        sType = VK_STRUCTURE_TYPE_WAYLAND_SURFACE_CREATE_INFO_KHR
        createInfo()
    }
    val surfaceVar = memScope.alloc<VkSurfaceKHRVar>()
    vkCreateWaylandSurfaceKHR!!(handle, surfaceCreateInfo.ptr, null, surfaceVar.ptr)
        .checkResult("Failed to create a Wayland surface")
    return Surface(handle, surfaceVar.value!!)
}
