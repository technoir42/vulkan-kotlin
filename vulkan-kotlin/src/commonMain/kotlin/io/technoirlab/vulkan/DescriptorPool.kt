package io.technoirlab.vulkan

import io.technoirlab.volk.VK_STRUCTURE_TYPE_DESCRIPTOR_SET_ALLOCATE_INFO
import io.technoirlab.volk.VkDescriptorPool
import io.technoirlab.volk.VkDescriptorSetAllocateInfo
import io.technoirlab.volk.VkDescriptorSetVar
import io.technoirlab.volk.VkDevice
import io.technoirlab.volk.vkAllocateDescriptorSets
import io.technoirlab.volk.vkDestroyDescriptorPool
import io.technoirlab.volk.vkFreeDescriptorSets
import io.technoirlab.volk.vkResetDescriptorPool
import kotlinx.cinterop.MemScope
import kotlinx.cinterop.alloc
import kotlinx.cinterop.allocArray
import kotlinx.cinterop.allocArrayOf
import kotlinx.cinterop.get
import kotlinx.cinterop.invoke
import kotlinx.cinterop.ptr

/**
 * Wrapper for [VkDescriptorPool].
 *
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/VkDescriptorPool.html">VkDescriptorPool Manual Page</a>
 */
class DescriptorPool(
    private val device: VkDevice,
    val handle: VkDescriptorPool
) : AutoCloseable {

    /**
     * Allocate one or more descriptor sets.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkAllocateDescriptorSets.html">vkAllocateDescriptorSets Manual Page</a>
     */
    context(memScope: MemScope)
    fun allocateDescriptorSets(setLayouts: List<DescriptorSetLayout>): List<DescriptorSet> {
        val layoutsNative = memScope.allocArrayOf(setLayouts.map { it.handle })
        val allocInfo = memScope.alloc<VkDescriptorSetAllocateInfo> {
            sType = VK_STRUCTURE_TYPE_DESCRIPTOR_SET_ALLOCATE_INFO
            descriptorPool = handle
            descriptorSetCount = setLayouts.size.toUInt()
            pSetLayouts = layoutsNative
        }
        val sets = memScope.allocArray<VkDescriptorSetVar>(setLayouts.size)
        vkAllocateDescriptorSets!!(device, allocInfo.ptr, sets)
            .checkResult("Failed to allocate descriptor sets")

        return (0 until setLayouts.size).map { DescriptorSet(sets[it]!!) }
    }

    /**
     * Free one or more descriptor sets.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkFreeDescriptorSets.html">vkFreeDescriptorSets Manual Page</a>
     */
    context(memScope: MemScope)
    fun freeDescriptorSets(descriptorSets: List<DescriptorSet>) {
        val descriptorSetHandles = memScope.allocArrayOf(descriptorSets.map { it.handle })
        vkFreeDescriptorSets!!(device, handle, descriptorSets.size.toUInt(), descriptorSetHandles)
    }

    /**
     * Reset the descriptor pool.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkResetDescriptorPool.html">vkResetDescriptorPool Manual Page</a>
     */
    fun reset(flags: UInt = 0u) {
        vkResetDescriptorPool!!(device, handle, flags).checkResult("Failed to reset descriptor pool")
    }

    /**
     * Destroy the descriptor pool.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkDestroyDescriptorPool.html">vkDestroyDescriptorPool Manual Page</a>
     */
    override fun close() {
        vkDestroyDescriptorPool!!(device, handle, null)
    }
}
