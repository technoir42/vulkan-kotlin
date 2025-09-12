package vulkan.kotlin

import kotlinx.cinterop.MemScope
import kotlinx.cinterop.alloc
import kotlinx.cinterop.invoke
import kotlinx.cinterop.ptr
import kotlinx.cinterop.value
import volk.VK_STRUCTURE_TYPE_METAL_SURFACE_CREATE_INFO_EXT
import volk.VkMetalSurfaceCreateInfoEXT
import volk.VkSurfaceKHRVar
import volk.vkCreateMetalSurfaceEXT

/**
 * Create a surface for CAMetalLayer.
 *
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCreateMetalSurfaceEXT.html">vkCreateMetalSurfaceEXT</a>
 */
context(memScope: MemScope)
fun Instance.createMetalSurface(createInfo: VkMetalSurfaceCreateInfoEXT.() -> Unit): Surface {
    val surfaceCreateInfo = memScope.alloc<VkMetalSurfaceCreateInfoEXT> {
        sType = VK_STRUCTURE_TYPE_METAL_SURFACE_CREATE_INFO_EXT
        createInfo()
    }
    val surfaceVar = memScope.alloc<VkSurfaceKHRVar>()
    vkCreateMetalSurfaceEXT!!(handle, surfaceCreateInfo.ptr, null, surfaceVar.ptr)
        .checkResult("Failed to create a Metal surface")
    return Surface(handle, surfaceVar.value!!)
}
