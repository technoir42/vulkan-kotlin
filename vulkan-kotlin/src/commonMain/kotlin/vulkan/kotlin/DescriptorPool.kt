package vulkan.kotlin

import kotlinx.cinterop.MemScope
import kotlinx.cinterop.alloc
import kotlinx.cinterop.allocArray
import kotlinx.cinterop.allocArrayOf
import kotlinx.cinterop.get
import kotlinx.cinterop.invoke
import kotlinx.cinterop.ptr
import volk.VK_STRUCTURE_TYPE_DESCRIPTOR_SET_ALLOCATE_INFO
import volk.VkDescriptorPool
import volk.VkDescriptorSetAllocateInfo
import volk.VkDescriptorSetVar
import volk.VkDevice
import volk.vkAllocateDescriptorSets
import volk.vkDestroyDescriptorPool
import volk.vkFreeDescriptorSets
import volk.vkResetDescriptorPool

class DescriptorPool(
    private val device: VkDevice,
    val handle: VkDescriptorPool
) : AutoCloseable {

    /**
     * Allocate one or more descriptor sets.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkAllocateDescriptorSets.html">vkAllocateDescriptorSets</a>
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
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkFreeDescriptorSets.html">vkFreeDescriptorSets</a>
     */
    context(memScope: MemScope)
    fun freeDescriptorSets(descriptorSets: List<DescriptorSet>) {
        val descriptorSetHandles = memScope.allocArrayOf(descriptorSets.map { it.handle })
        vkFreeDescriptorSets!!(device, handle, descriptorSets.size.toUInt(), descriptorSetHandles)
    }

    /**
     * Reset the descriptor pool.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkResetDescriptorPool.html">vkResetDescriptorPool</a>
     */
    fun reset(flags: UInt = 0u) {
        vkResetDescriptorPool!!(device, handle, flags).checkResult("Failed to reset descriptor pool")
    }

    /**
     * Destroy the descriptor pool.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkDestroyDescriptorPool.html">vkDestroyDescriptorPool</a>
     */
    override fun close() {
        vkDestroyDescriptorPool!!(device, handle, null)
    }
}
