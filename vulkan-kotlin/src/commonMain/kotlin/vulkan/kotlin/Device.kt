package vulkan.kotlin

import kotlinx.cinterop.MemScope
import kotlinx.cinterop.alloc
import kotlinx.cinterop.invoke
import kotlinx.cinterop.ptr
import kotlinx.cinterop.value
import volk.VK_STRUCTURE_TYPE_BUFFER_CREATE_INFO
import volk.VK_STRUCTURE_TYPE_COMMAND_POOL_CREATE_INFO
import volk.VK_STRUCTURE_TYPE_DESCRIPTOR_POOL_CREATE_INFO
import volk.VK_STRUCTURE_TYPE_DESCRIPTOR_SET_LAYOUT_CREATE_INFO
import volk.VK_STRUCTURE_TYPE_FENCE_CREATE_INFO
import volk.VK_STRUCTURE_TYPE_GRAPHICS_PIPELINE_CREATE_INFO
import volk.VK_STRUCTURE_TYPE_IMAGE_CREATE_INFO
import volk.VK_STRUCTURE_TYPE_IMAGE_VIEW_CREATE_INFO
import volk.VK_STRUCTURE_TYPE_MEMORY_ALLOCATE_INFO
import volk.VK_STRUCTURE_TYPE_PIPELINE_LAYOUT_CREATE_INFO
import volk.VK_STRUCTURE_TYPE_SAMPLER_CREATE_INFO
import volk.VK_STRUCTURE_TYPE_SEMAPHORE_CREATE_INFO
import volk.VK_STRUCTURE_TYPE_SHADER_MODULE_CREATE_INFO
import volk.VK_STRUCTURE_TYPE_SWAPCHAIN_CREATE_INFO_KHR
import volk.VkBufferCreateInfo
import volk.VkBufferVar
import volk.VkCommandPoolCreateInfo
import volk.VkCommandPoolVar
import volk.VkDescriptorPoolCreateInfo
import volk.VkDescriptorPoolVar
import volk.VkDescriptorSetLayoutCreateInfo
import volk.VkDescriptorSetLayoutVar
import volk.VkDevice
import volk.VkDeviceMemoryVar
import volk.VkFenceCreateInfo
import volk.VkFenceVar
import volk.VkGraphicsPipelineCreateInfo
import volk.VkImageCreateInfo
import volk.VkImageVar
import volk.VkImageViewCreateInfo
import volk.VkImageViewVar
import volk.VkMemoryAllocateInfo
import volk.VkPipelineLayoutCreateInfo
import volk.VkPipelineLayoutVar
import volk.VkPipelineVar
import volk.VkQueueVar
import volk.VkSamplerCreateInfo
import volk.VkSamplerVar
import volk.VkSemaphoreCreateInfo
import volk.VkSemaphoreVar
import volk.VkShaderModuleCreateInfo
import volk.VkShaderModuleVar
import volk.VkSwapchainCreateInfoKHR
import volk.VkSwapchainKHRVar
import volk.vkAllocateMemory
import volk.vkCreateBuffer
import volk.vkCreateCommandPool
import volk.vkCreateDescriptorPool
import volk.vkCreateDescriptorSetLayout
import volk.vkCreateFence
import volk.vkCreateGraphicsPipelines
import volk.vkCreateImage
import volk.vkCreateImageView
import volk.vkCreatePipelineLayout
import volk.vkCreateSampler
import volk.vkCreateSemaphore
import volk.vkCreateShaderModule
import volk.vkCreateSwapchainKHR
import volk.vkDestroyDevice
import volk.vkDeviceWaitIdle
import volk.vkGetDeviceQueue
import volk.volkLoadDevice

class Device(
    val handle: VkDevice
) : AutoCloseable {

    init {
        volkLoadDevice(handle)
    }

    /**
     * Allocate device memory.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkAllocateMemory.html">vkAllocateMemory</a>
     */
    context(memScope: MemScope)
    fun allocateMemory(allocateInfo: VkMemoryAllocateInfo.() -> Unit): DeviceMemory {
        val memoryAllocateInfo = memScope.alloc<VkMemoryAllocateInfo> {
            sType = VK_STRUCTURE_TYPE_MEMORY_ALLOCATE_INFO
            allocateInfo()
        }
        val memory = memScope.alloc<VkDeviceMemoryVar>()
        vkAllocateMemory!!(handle, memoryAllocateInfo.ptr, null, memory.ptr)
            .checkResult("Failed to allocate memory")
        return DeviceMemory(handle, memory.value!!, memoryAllocateInfo.allocationSize)
    }

    /**
     * Create a new buffer.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCreateBuffer.html">vkCreateBuffer</a>
     */
    context(memScope: MemScope)
    fun createBuffer(createInfo: VkBufferCreateInfo.() -> Unit): Buffer {
        val createInfo = memScope.alloc<VkBufferCreateInfo> {
            sType = VK_STRUCTURE_TYPE_BUFFER_CREATE_INFO
            createInfo()
        }
        val bufferVar = memScope.alloc<VkBufferVar>()
        vkCreateBuffer!!(handle, createInfo.ptr, null, bufferVar.ptr)
            .checkResult("Failed to create a buffer")
        return Buffer(handle, bufferVar.value!!, createInfo.size)
    }

    /**
     * Create a new command pool.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCreateCommandPool.html">vkCreateCommandPool</a>
     */
    context(memScope: MemScope)
    fun createCommandPool(createInfo: VkCommandPoolCreateInfo.() -> Unit): CommandPool {
        val createInfo = memScope.alloc<VkCommandPoolCreateInfo> {
            sType = VK_STRUCTURE_TYPE_COMMAND_POOL_CREATE_INFO
            createInfo()
        }
        val commandPoolVar = memScope.alloc<VkCommandPoolVar>()
        vkCreateCommandPool!!(handle, createInfo.ptr, null, commandPoolVar.ptr)
            .checkResult("Failed to create a command pool")
        return CommandPool(handle, commandPoolVar.value!!)
    }

    /**
     * Create a new descriptor pool.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCreateDescriptorPool.html">vkCreateDescriptorPool</a>
     */
    context(memScope: MemScope)
    fun createDescriptorPool(createInfo: VkDescriptorPoolCreateInfo.() -> Unit): DescriptorPool {
        val info = memScope.alloc<VkDescriptorPoolCreateInfo> {
            sType = VK_STRUCTURE_TYPE_DESCRIPTOR_POOL_CREATE_INFO
            createInfo()
        }
        val poolVar = memScope.alloc<VkDescriptorPoolVar>()
        vkCreateDescriptorPool!!(handle, info.ptr, null, poolVar.ptr)
            .checkResult("Failed to create descriptor pool")
        return DescriptorPool(handle, poolVar.value!!)
    }

    /**
     * Create a new descriptor set layout.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCreateDescriptorSetLayout.html">vkCreateDescriptorSetLayout</a>
     */
    context(memScope: MemScope)
    fun createDescriptorSetLayout(createInfo: VkDescriptorSetLayoutCreateInfo.() -> Unit): DescriptorSetLayout {
        val info = memScope.alloc<VkDescriptorSetLayoutCreateInfo> {
            sType = VK_STRUCTURE_TYPE_DESCRIPTOR_SET_LAYOUT_CREATE_INFO
            createInfo()
        }
        val layoutVar = memScope.alloc<VkDescriptorSetLayoutVar>()
        vkCreateDescriptorSetLayout!!(handle, info.ptr, null, layoutVar.ptr)
            .checkResult("Failed to create descriptor set layout")
        return DescriptorSetLayout(handle, layoutVar.value!!)
    }

    /**
     * Create a new image.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCreateImage.html">vkCreateImage</a>
     */
    context(memScope: MemScope)
    fun createImage(createInfo: VkImageCreateInfo.() -> Unit): Image {
        val createInfo = memScope.alloc<VkImageCreateInfo> {
            sType = VK_STRUCTURE_TYPE_IMAGE_CREATE_INFO
            createInfo()
        }
        val imageVar = memScope.alloc<VkImageVar>()
        vkCreateImage!!(handle, createInfo.ptr, null, imageVar.ptr)
            .checkResult("Failed to create an image")
        return Image(handle, imageVar.value!!)
    }

    /**
     * Create an image view from an existing image.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCreateImageView.html">vkCreateImageView</a>
     */
    context(memScope: MemScope)
    fun createImageView(createInfo: VkImageViewCreateInfo.() -> Unit): ImageView {
        val createInfo = memScope.alloc<VkImageViewCreateInfo> {
            sType = VK_STRUCTURE_TYPE_IMAGE_VIEW_CREATE_INFO
            createInfo()
        }
        val imageViewVar = memScope.alloc<VkImageViewVar>()
        vkCreateImageView!!(handle, createInfo.ptr, null, imageViewVar.ptr)
            .checkResult("Failed to create an image view")
        return ImageView(handle, imageViewVar.value!!)
    }

    /**
     * Create a new fence.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCreateFence.html">vkCreateFence</a>
     */
    context(memScope: MemScope)
    fun createFence(createInfo: VkFenceCreateInfo.() -> Unit = {}): Fence {
        val fenceInfo = memScope.alloc<VkFenceCreateInfo> {
            sType = VK_STRUCTURE_TYPE_FENCE_CREATE_INFO
            createInfo()
        }
        val fenceVar = memScope.alloc<VkFenceVar>()
        vkCreateFence!!(handle, fenceInfo.ptr, null, fenceVar.ptr)
            .checkResult("Failed to create a fence")
        return Fence(handle, fenceVar.value!!)
    }

    /**
     * Create a new graphics pipeline.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCreateGraphicsPipelines.html">vkCreateGraphicsPipelines</a>
     */
    context(memScope: MemScope)
    fun createGraphicsPipeline(createInfo: VkGraphicsPipelineCreateInfo.() -> Unit): Pipeline {
        val info = memScope.alloc<VkGraphicsPipelineCreateInfo> {
            sType = VK_STRUCTURE_TYPE_GRAPHICS_PIPELINE_CREATE_INFO
            createInfo()
        }
        val pipelineVar = memScope.alloc<VkPipelineVar>()
        vkCreateGraphicsPipelines!!(handle, null, 1u, info.ptr, null, pipelineVar.ptr)
            .checkResult("Failed to create graphics pipeline")
        return Pipeline(handle, pipelineVar.value!!)
    }

    /**
     * Create a new pipeline layout.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCreatePipelineLayout.html">vkCreatePipelineLayout</a>
     */
    context(memScope: MemScope)
    fun createPipelineLayout(createInfo: VkPipelineLayoutCreateInfo.() -> Unit): PipelineLayout {
        val info = memScope.alloc<VkPipelineLayoutCreateInfo> {
            sType = VK_STRUCTURE_TYPE_PIPELINE_LAYOUT_CREATE_INFO
            createInfo()
        }
        val layoutVar = memScope.alloc<VkPipelineLayoutVar>()
        vkCreatePipelineLayout!!(handle, info.ptr, null, layoutVar.ptr)
            .checkResult("Failed to create pipeline layout")
        return PipelineLayout(handle, layoutVar.value!!)
    }

    /**
     * Create a new sampler.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCreateSampler.html">vkCreateSampler</a>
     */
    context(memScope: MemScope)
    fun createSampler(createInfo: VkSamplerCreateInfo.() -> Unit): Sampler {
        val info = memScope.alloc<VkSamplerCreateInfo> {
            sType = VK_STRUCTURE_TYPE_SAMPLER_CREATE_INFO
            createInfo()
        }
        val samplerVar = memScope.alloc<VkSamplerVar>()
        vkCreateSampler!!(handle, info.ptr, null, samplerVar.ptr)
            .checkResult("Failed to create sampler")
        return Sampler(handle, samplerVar.value!!)
    }

    /**
     * Create a new queue semaphore.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCreateSemaphore.html">vkCreateSemaphore</a>
     */
    context(memScope: MemScope)
    fun createSemaphore(createInfo: VkSemaphoreCreateInfo.() -> Unit = {}): Semaphore {
        val semaphoreInfo = memScope.alloc<VkSemaphoreCreateInfo> {
            sType = VK_STRUCTURE_TYPE_SEMAPHORE_CREATE_INFO
            createInfo()
        }
        val semaphore = memScope.alloc<VkSemaphoreVar>()
        vkCreateSemaphore!!(handle, semaphoreInfo.ptr, null, semaphore.ptr)
            .checkResult("Failed to create a semaphore")
        return Semaphore(handle, semaphore.value!!)
    }

    /**
     * Create a new shader module.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCreateShaderModule.html">vkCreateShaderModule</a>
     */
    context(memScope: MemScope)
    fun createShaderModule(createInfo: VkShaderModuleCreateInfo.() -> Unit): ShaderModule {
        val info = memScope.alloc<VkShaderModuleCreateInfo> {
            sType = VK_STRUCTURE_TYPE_SHADER_MODULE_CREATE_INFO
            createInfo()
        }
        val shaderModule = memScope.alloc<VkShaderModuleVar>()
        vkCreateShaderModule!!(handle, info.ptr, null, shaderModule.ptr)
            .checkResult("Failed to create shader module")
        return ShaderModule(handle, shaderModule.value!!)
    }

    /**
     * Create a swapchain.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCreateSwapchainKHR.html">vkCreateSwapchainKHR</a>
     */
    context(memScope: MemScope)
    fun createSwapchain(createInfo: VkSwapchainCreateInfoKHR.() -> Unit): Swapchain {
        val swapChainCreateInfo = memScope.alloc<VkSwapchainCreateInfoKHR> {
            sType = VK_STRUCTURE_TYPE_SWAPCHAIN_CREATE_INFO_KHR
            createInfo()
        }
        val swapChainVar = memScope.alloc<VkSwapchainKHRVar>()
        vkCreateSwapchainKHR!!(handle, swapChainCreateInfo.ptr, null, swapChainVar.ptr)
            .checkResult("Failed to create a swap chain")
        return Swapchain(handle, swapChainVar.value!!)
    }

    /**
     * Get a queue from the device.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkGetDeviceQueue.html">vkGetDeviceQueue</a>
     */
    context(memScope: MemScope)
    fun getQueue(queueFamilyIndex: UInt, queueIndex: UInt = 0u): Queue {
        val queueVar = memScope.alloc<VkQueueVar>()
        vkGetDeviceQueue!!(handle, queueFamilyIndex, queueIndex, queueVar.ptr)
        return Queue(queueVar.value!!, queueFamilyIndex)
    }

    /**
     * Wait for the device to become idle.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkDeviceWaitIdle.html">vkDeviceWaitIdle</a>
     */
    fun waitIdle() {
        vkDeviceWaitIdle!!(handle).checkResult("Failed to wait for device idle")
    }

    /**
     * Destroy the logical device.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkDestroyDevice.html">vkDestroyDevice</a>
     */
    override fun close() {
        vkDestroyDevice!!(handle, null)
    }
}
