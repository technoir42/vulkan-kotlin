package io.technoirlab.vulkan

import io.technoirlab.volk.VK_STRUCTURE_TYPE_APPLICATION_INFO
import io.technoirlab.volk.VK_STRUCTURE_TYPE_INSTANCE_CREATE_INFO
import io.technoirlab.volk.VkApplicationInfo
import io.technoirlab.volk.VkExtensionProperties
import io.technoirlab.volk.VkInstanceCreateInfo
import io.technoirlab.volk.VkInstanceVar
import io.technoirlab.volk.VkLayerProperties
import io.technoirlab.volk.vkCreateInstance
import io.technoirlab.volk.vkEnumerateInstanceExtensionProperties
import io.technoirlab.volk.vkEnumerateInstanceLayerProperties
import io.technoirlab.volk.volkFinalize
import io.technoirlab.volk.volkGetInstanceVersion
import io.technoirlab.volk.volkInitialize
import kotlinx.cinterop.MemScope
import kotlinx.cinterop.UIntVar
import kotlinx.cinterop.alloc
import kotlinx.cinterop.allocArray
import kotlinx.cinterop.get
import kotlinx.cinterop.invoke
import kotlinx.cinterop.ptr
import kotlinx.cinterop.value

/**
 * Vulkan instance loader.
 */
class Vulkan : AutoCloseable {
    init {
        volkInitialize().checkResult("Failed to initialize Volk")
    }

    /**
     * Get the Vulkan instance version.
     */
    val instanceVersion: UInt
        get() = volkGetInstanceVersion()

    /**
     * Unload Vulkan.
     */
    override fun close() {
        volkFinalize()
    }

    /**
     * Create a new Vulkan instance.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCreateInstance.html">vkCreateInstance</a>
     */
    context(memScope: MemScope)
    fun createInstance(applicationInfo: VkApplicationInfo.() -> Unit = {}, instanceInfo: VkInstanceCreateInfo.() -> Unit = {}): Instance {
        val applicationInfo = memScope.alloc<VkApplicationInfo> {
            sType = VK_STRUCTURE_TYPE_APPLICATION_INFO
            applicationInfo()
        }
        val instanceCreateInfo = memScope.alloc<VkInstanceCreateInfo> {
            sType = VK_STRUCTURE_TYPE_INSTANCE_CREATE_INFO
            pApplicationInfo = applicationInfo.ptr
            instanceInfo()
        }
        val instanceVar = memScope.alloc<VkInstanceVar>()
        vkCreateInstance!!(instanceCreateInfo.ptr, null, instanceVar.ptr)
            .checkResult("Failed to create a Vulkan instance")
        return Instance(instanceVar.value!!)
    }

    /**
     * Enumerate global extension properties.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkEnumerateInstanceExtensionProperties.html">vkEnumerateInstanceExtensionProperties</a>
     */
    context(memScope: MemScope)
    fun enumerateInstanceExtensionProperties(): Sequence<VkExtensionProperties> {
        val countVar = memScope.alloc<UIntVar>()
        vkEnumerateInstanceExtensionProperties!!(null, countVar.ptr, null)
            .checkResult("Failed to enumerate instance extensions")

        val count = countVar.value.toInt()
        if (count == 0) return emptySequence()

        val extensionProperties = memScope.allocArray<VkExtensionProperties>(count)
        vkEnumerateInstanceExtensionProperties!!(null, countVar.ptr, extensionProperties)
            .checkResult("Failed to enumerate instance extensions")

        return (0 until count).asSequence().map { extensionProperties[it] }
    }

    /**
     * Enumerate global layer properties.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkEnumerateInstanceLayerProperties.html">vkEnumerateInstanceLayerProperties</a>
     */
    context(memScope: MemScope)
    fun enumerateInstanceLayerProperties(): Sequence<VkLayerProperties> {
        val countVar = memScope.alloc<UIntVar>()
        vkEnumerateInstanceLayerProperties!!(countVar.ptr, null)
            .checkResult("Failed to enumerate instance layers")

        val count = countVar.value.toInt()
        if (count == 0) return emptySequence()

        val layerProperties = memScope.allocArray<VkLayerProperties>(count)
        vkEnumerateInstanceLayerProperties!!(countVar.ptr, layerProperties)
            .checkResult("Failed to enumerate instance layers")

        return (0 until count).asSequence().map { layerProperties[it] }
    }
}
