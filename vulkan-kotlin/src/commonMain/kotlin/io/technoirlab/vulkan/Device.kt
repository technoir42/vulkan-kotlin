package io.technoirlab.vulkan

import io.technoirlab.volk.VK_STRUCTURE_TYPE_BUFFER_CREATE_INFO
import io.technoirlab.volk.VK_STRUCTURE_TYPE_BUFFER_VIEW_CREATE_INFO
import io.technoirlab.volk.VK_STRUCTURE_TYPE_COMMAND_POOL_CREATE_INFO
import io.technoirlab.volk.VK_STRUCTURE_TYPE_DESCRIPTOR_POOL_CREATE_INFO
import io.technoirlab.volk.VK_STRUCTURE_TYPE_DESCRIPTOR_SET_LAYOUT_CREATE_INFO
import io.technoirlab.volk.VK_STRUCTURE_TYPE_FENCE_CREATE_INFO
import io.technoirlab.volk.VK_STRUCTURE_TYPE_GRAPHICS_PIPELINE_CREATE_INFO
import io.technoirlab.volk.VK_STRUCTURE_TYPE_IMAGE_CREATE_INFO
import io.technoirlab.volk.VK_STRUCTURE_TYPE_IMAGE_VIEW_CREATE_INFO
import io.technoirlab.volk.VK_STRUCTURE_TYPE_MEMORY_ALLOCATE_INFO
import io.technoirlab.volk.VK_STRUCTURE_TYPE_PIPELINE_CACHE_CREATE_INFO
import io.technoirlab.volk.VK_STRUCTURE_TYPE_PIPELINE_COLOR_BLEND_STATE_CREATE_INFO
import io.technoirlab.volk.VK_STRUCTURE_TYPE_PIPELINE_DEPTH_STENCIL_STATE_CREATE_INFO
import io.technoirlab.volk.VK_STRUCTURE_TYPE_PIPELINE_DYNAMIC_STATE_CREATE_INFO
import io.technoirlab.volk.VK_STRUCTURE_TYPE_PIPELINE_INPUT_ASSEMBLY_STATE_CREATE_INFO
import io.technoirlab.volk.VK_STRUCTURE_TYPE_PIPELINE_LAYOUT_CREATE_INFO
import io.technoirlab.volk.VK_STRUCTURE_TYPE_PIPELINE_MULTISAMPLE_STATE_CREATE_INFO
import io.technoirlab.volk.VK_STRUCTURE_TYPE_PIPELINE_RASTERIZATION_STATE_CREATE_INFO
import io.technoirlab.volk.VK_STRUCTURE_TYPE_PIPELINE_RENDERING_CREATE_INFO
import io.technoirlab.volk.VK_STRUCTURE_TYPE_PIPELINE_SHADER_STAGE_CREATE_INFO
import io.technoirlab.volk.VK_STRUCTURE_TYPE_PIPELINE_TESSELLATION_STATE_CREATE_INFO
import io.technoirlab.volk.VK_STRUCTURE_TYPE_PIPELINE_VERTEX_INPUT_STATE_CREATE_INFO
import io.technoirlab.volk.VK_STRUCTURE_TYPE_PIPELINE_VIEWPORT_STATE_CREATE_INFO
import io.technoirlab.volk.VK_STRUCTURE_TYPE_QUERY_POOL_CREATE_INFO
import io.technoirlab.volk.VK_STRUCTURE_TYPE_SAMPLER_CREATE_INFO
import io.technoirlab.volk.VK_STRUCTURE_TYPE_SEMAPHORE_CREATE_INFO
import io.technoirlab.volk.VK_STRUCTURE_TYPE_SHADER_MODULE_CREATE_INFO
import io.technoirlab.volk.VK_STRUCTURE_TYPE_SWAPCHAIN_CREATE_INFO_KHR
import io.technoirlab.volk.VkBufferCreateInfo
import io.technoirlab.volk.VkBufferVar
import io.technoirlab.volk.VkBufferViewCreateInfo
import io.technoirlab.volk.VkBufferViewVar
import io.technoirlab.volk.VkCommandPoolCreateInfo
import io.technoirlab.volk.VkCommandPoolVar
import io.technoirlab.volk.VkCopyDescriptorSet
import io.technoirlab.volk.VkDescriptorPoolCreateInfo
import io.technoirlab.volk.VkDescriptorPoolVar
import io.technoirlab.volk.VkDescriptorSetLayoutCreateInfo
import io.technoirlab.volk.VkDescriptorSetLayoutVar
import io.technoirlab.volk.VkDevice
import io.technoirlab.volk.VkDeviceMemoryVar
import io.technoirlab.volk.VkFenceCreateInfo
import io.technoirlab.volk.VkFenceVar
import io.technoirlab.volk.VkGraphicsPipelineCreateInfo
import io.technoirlab.volk.VkImageCreateInfo
import io.technoirlab.volk.VkImageVar
import io.technoirlab.volk.VkImageViewCreateInfo
import io.technoirlab.volk.VkImageViewVar
import io.technoirlab.volk.VkMemoryAllocateInfo
import io.technoirlab.volk.VkPipelineCacheCreateInfo
import io.technoirlab.volk.VkPipelineCacheVar
import io.technoirlab.volk.VkPipelineColorBlendStateCreateInfo
import io.technoirlab.volk.VkPipelineCreateFlags
import io.technoirlab.volk.VkPipelineDepthStencilStateCreateInfo
import io.technoirlab.volk.VkPipelineDynamicStateCreateInfo
import io.technoirlab.volk.VkPipelineInputAssemblyStateCreateInfo
import io.technoirlab.volk.VkPipelineLayoutCreateInfo
import io.technoirlab.volk.VkPipelineLayoutVar
import io.technoirlab.volk.VkPipelineMultisampleStateCreateInfo
import io.technoirlab.volk.VkPipelineRasterizationStateCreateInfo
import io.technoirlab.volk.VkPipelineRenderingCreateInfo
import io.technoirlab.volk.VkPipelineShaderStageCreateInfo
import io.technoirlab.volk.VkPipelineTessellationStateCreateInfo
import io.technoirlab.volk.VkPipelineVar
import io.technoirlab.volk.VkPipelineVertexInputStateCreateInfo
import io.technoirlab.volk.VkPipelineViewportStateCreateInfo
import io.technoirlab.volk.VkQueryPoolCreateInfo
import io.technoirlab.volk.VkQueryPoolVar
import io.technoirlab.volk.VkQueueVar
import io.technoirlab.volk.VkSamplerCreateInfo
import io.technoirlab.volk.VkSamplerVar
import io.technoirlab.volk.VkSemaphoreCreateInfo
import io.technoirlab.volk.VkSemaphoreVar
import io.technoirlab.volk.VkShaderModuleCreateInfo
import io.technoirlab.volk.VkShaderModuleVar
import io.technoirlab.volk.VkSwapchainCreateInfoKHR
import io.technoirlab.volk.VkSwapchainKHRVar
import io.technoirlab.volk.VkWriteDescriptorSet
import io.technoirlab.volk.vkAllocateMemory
import io.technoirlab.volk.vkCreateBuffer
import io.technoirlab.volk.vkCreateBufferView
import io.technoirlab.volk.vkCreateCommandPool
import io.technoirlab.volk.vkCreateDescriptorPool
import io.technoirlab.volk.vkCreateDescriptorSetLayout
import io.technoirlab.volk.vkCreateFence
import io.technoirlab.volk.vkCreateGraphicsPipelines
import io.technoirlab.volk.vkCreateImage
import io.technoirlab.volk.vkCreateImageView
import io.technoirlab.volk.vkCreatePipelineCache
import io.technoirlab.volk.vkCreatePipelineLayout
import io.technoirlab.volk.vkCreateQueryPool
import io.technoirlab.volk.vkCreateSampler
import io.technoirlab.volk.vkCreateSemaphore
import io.technoirlab.volk.vkCreateShaderModule
import io.technoirlab.volk.vkCreateSwapchainKHR
import io.technoirlab.volk.vkDestroyDevice
import io.technoirlab.volk.vkDeviceWaitIdle
import io.technoirlab.volk.vkGetDeviceQueue
import io.technoirlab.volk.vkUpdateDescriptorSets
import io.technoirlab.volk.volkLoadDevice
import kotlinx.cinterop.MemScope
import kotlinx.cinterop.alloc
import kotlinx.cinterop.allocArray
import kotlinx.cinterop.invoke
import kotlinx.cinterop.ptr
import kotlinx.cinterop.value

/**
 * Wrapper for [VkDevice].
 *
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/VkDevice.html">VkDevice</a>
 */
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
        val bufferCreateInfo = memScope.alloc<VkBufferCreateInfo> {
            sType = VK_STRUCTURE_TYPE_BUFFER_CREATE_INFO
            createInfo()
        }
        val bufferVar = memScope.alloc<VkBufferVar>()
        vkCreateBuffer!!(handle, bufferCreateInfo.ptr, null, bufferVar.ptr)
            .checkResult("Failed to create a buffer")
        return Buffer(handle, bufferVar.value!!, bufferCreateInfo.size)
    }

    /**
     * Create a new buffer view.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCreateBufferView.html">vkCreateBufferView</a>
     */
    context(memScope: MemScope)
    fun createBufferView(createInfo: VkBufferViewCreateInfo.() -> Unit): BufferView {
        val bufferViewCreateInfo = memScope.alloc<VkBufferViewCreateInfo> {
            sType = VK_STRUCTURE_TYPE_BUFFER_VIEW_CREATE_INFO
            createInfo()
        }
        val bufferViewVar = memScope.alloc<VkBufferViewVar>()
        vkCreateBufferView!!(handle, bufferViewCreateInfo.ptr, null, bufferViewVar.ptr)
            .checkResult("Failed to create buffer view")
        return BufferView(handle, bufferViewVar.value!!)
    }

    /**
     * Create a new command pool.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCreateCommandPool.html">vkCreateCommandPool</a>
     */
    context(memScope: MemScope)
    fun createCommandPool(createInfo: VkCommandPoolCreateInfo.() -> Unit): CommandPool {
        val commandPoolCreateInfo = memScope.alloc<VkCommandPoolCreateInfo> {
            sType = VK_STRUCTURE_TYPE_COMMAND_POOL_CREATE_INFO
            createInfo()
        }
        val commandPoolVar = memScope.alloc<VkCommandPoolVar>()
        vkCreateCommandPool!!(handle, commandPoolCreateInfo.ptr, null, commandPoolVar.ptr)
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
        val descriptorPoolCreateInfo = memScope.alloc<VkDescriptorPoolCreateInfo> {
            sType = VK_STRUCTURE_TYPE_DESCRIPTOR_POOL_CREATE_INFO
            createInfo()
        }
        val poolVar = memScope.alloc<VkDescriptorPoolVar>()
        vkCreateDescriptorPool!!(handle, descriptorPoolCreateInfo.ptr, null, poolVar.ptr)
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
        val descriptorSetLayoutCreateInfo = memScope.alloc<VkDescriptorSetLayoutCreateInfo> {
            sType = VK_STRUCTURE_TYPE_DESCRIPTOR_SET_LAYOUT_CREATE_INFO
            createInfo()
        }
        val layoutVar = memScope.alloc<VkDescriptorSetLayoutVar>()
        vkCreateDescriptorSetLayout!!(handle, descriptorSetLayoutCreateInfo.ptr, null, layoutVar.ptr)
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
        val imageCreateInfo = memScope.alloc<VkImageCreateInfo> {
            sType = VK_STRUCTURE_TYPE_IMAGE_CREATE_INFO
            createInfo()
        }
        val imageVar = memScope.alloc<VkImageVar>()
        vkCreateImage!!(handle, imageCreateInfo.ptr, null, imageVar.ptr)
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
        val imageViewCreateInfo = memScope.alloc<VkImageViewCreateInfo> {
            sType = VK_STRUCTURE_TYPE_IMAGE_VIEW_CREATE_INFO
            createInfo()
        }
        val imageViewVar = memScope.alloc<VkImageViewVar>()
        vkCreateImageView!!(handle, imageViewCreateInfo.ptr, null, imageViewVar.ptr)
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
    @Suppress("LongParameterList", "LongMethod")
    fun createGraphicsPipeline(
        layout: PipelineLayout,
        stageCount: UInt,
        stages: VkPipelineShaderStageCreateInfo.(UInt) -> Unit = {},
        vertexInputState: VkPipelineVertexInputStateCreateInfo.() -> Unit = {},
        inputAssemblyState: VkPipelineInputAssemblyStateCreateInfo.() -> Unit = {},
        tessellationState: VkPipelineTessellationStateCreateInfo.() -> Unit = {},
        viewportState: VkPipelineViewportStateCreateInfo.() -> Unit = {},
        rasterizationState: VkPipelineRasterizationStateCreateInfo.() -> Unit = {},
        multisampleState: VkPipelineMultisampleStateCreateInfo.() -> Unit = {},
        depthStencilState: VkPipelineDepthStencilStateCreateInfo.() -> Unit = {},
        colorBlendState: VkPipelineColorBlendStateCreateInfo.() -> Unit = {},
        dynamicState: VkPipelineDynamicStateCreateInfo.() -> Unit = {},
        renderingCreateInfo: VkPipelineRenderingCreateInfo.() -> Unit = {},
        flags: VkPipelineCreateFlags = 0u,
        basePipeline: Pipeline? = null,
        cache: PipelineCache? = null
    ): Pipeline {
        val shaderStageCreateInfo = memScope.allocArray<VkPipelineShaderStageCreateInfo>(stageCount.toLong()) {
            sType = VK_STRUCTURE_TYPE_PIPELINE_SHADER_STAGE_CREATE_INFO
            stages(it.toUInt())
        }
        val vertexInputStateCreateInfo = memScope.alloc<VkPipelineVertexInputStateCreateInfo> {
            sType = VK_STRUCTURE_TYPE_PIPELINE_VERTEX_INPUT_STATE_CREATE_INFO
            vertexInputState()
        }
        val inputAssemblyStateCreateInfo = memScope.alloc<VkPipelineInputAssemblyStateCreateInfo> {
            sType = VK_STRUCTURE_TYPE_PIPELINE_INPUT_ASSEMBLY_STATE_CREATE_INFO
            inputAssemblyState()
        }
        val tessellationStateCreateInfo = memScope.alloc<VkPipelineTessellationStateCreateInfo> {
            sType = VK_STRUCTURE_TYPE_PIPELINE_TESSELLATION_STATE_CREATE_INFO
            tessellationState()
        }
        val viewportStateCreateInfo = memScope.alloc<VkPipelineViewportStateCreateInfo> {
            sType = VK_STRUCTURE_TYPE_PIPELINE_VIEWPORT_STATE_CREATE_INFO
            viewportState()
        }
        val rasterizationStateCreateInfo = memScope.alloc<VkPipelineRasterizationStateCreateInfo> {
            sType = VK_STRUCTURE_TYPE_PIPELINE_RASTERIZATION_STATE_CREATE_INFO
            rasterizationState()
        }
        val multisampleStateCreateInfo = memScope.alloc<VkPipelineMultisampleStateCreateInfo> {
            sType = VK_STRUCTURE_TYPE_PIPELINE_MULTISAMPLE_STATE_CREATE_INFO
            multisampleState()
        }
        val depthStencilStateCreateInfo = memScope.alloc<VkPipelineDepthStencilStateCreateInfo> {
            sType = VK_STRUCTURE_TYPE_PIPELINE_DEPTH_STENCIL_STATE_CREATE_INFO
            depthStencilState()
        }
        val colorBlendStateCreateInfo = memScope.alloc<VkPipelineColorBlendStateCreateInfo> {
            sType = VK_STRUCTURE_TYPE_PIPELINE_COLOR_BLEND_STATE_CREATE_INFO
            colorBlendState()
        }
        val dynamicStateCreateInfo = memScope.alloc<VkPipelineDynamicStateCreateInfo> {
            sType = VK_STRUCTURE_TYPE_PIPELINE_DYNAMIC_STATE_CREATE_INFO
            dynamicState()
        }
        val renderingCreateInfo = memScope.alloc<VkPipelineRenderingCreateInfo> {
            sType = VK_STRUCTURE_TYPE_PIPELINE_RENDERING_CREATE_INFO
            renderingCreateInfo()
        }
        val graphicsPipelineCreateInfo = memScope.alloc<VkGraphicsPipelineCreateInfo> {
            sType = VK_STRUCTURE_TYPE_GRAPHICS_PIPELINE_CREATE_INFO
            this.flags = flags
            this.stageCount = stageCount
            this.layout = layout.handle
            pStages = shaderStageCreateInfo
            pVertexInputState = vertexInputStateCreateInfo.ptr
            pInputAssemblyState = inputAssemblyStateCreateInfo.ptr
            pTessellationState = tessellationStateCreateInfo.ptr
            pViewportState = viewportStateCreateInfo.ptr
            pRasterizationState = rasterizationStateCreateInfo.ptr
            pMultisampleState = multisampleStateCreateInfo.ptr
            pDepthStencilState = depthStencilStateCreateInfo.ptr
            pColorBlendState = colorBlendStateCreateInfo.ptr
            pDynamicState = dynamicStateCreateInfo.ptr
            pNext = renderingCreateInfo.ptr
            basePipelineHandle = basePipeline?.handle
        }
        val pipelineVar = memScope.alloc<VkPipelineVar>()
        vkCreateGraphicsPipelines!!(handle, cache?.handle, 1u, graphicsPipelineCreateInfo.ptr, null, pipelineVar.ptr)
            .checkResult("Failed to create graphics pipeline")
        return Pipeline(handle, pipelineVar.value!!)
    }

    /**
     * Create a new pipeline cache.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCreatePipelineCache.html">vkCreatePipelineCache</a>
     */
    context(memScope: MemScope)
    fun createPipelineCache(createInfo: VkPipelineCacheCreateInfo.() -> Unit = {}): PipelineCache {
        val pipelineCacheCreateInfo = memScope.alloc<VkPipelineCacheCreateInfo> {
            sType = VK_STRUCTURE_TYPE_PIPELINE_CACHE_CREATE_INFO
            createInfo()
        }
        val pipelineCacheVar = memScope.alloc<VkPipelineCacheVar>()
        vkCreatePipelineCache!!(handle, pipelineCacheCreateInfo.ptr, null, pipelineCacheVar.ptr)
            .checkResult("Failed to create pipeline cache")
        return PipelineCache(handle, pipelineCacheVar.value!!)
    }

    /**
     * Create a new pipeline layout.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCreatePipelineLayout.html">vkCreatePipelineLayout</a>
     */
    context(memScope: MemScope)
    fun createPipelineLayout(createInfo: VkPipelineLayoutCreateInfo.() -> Unit = {}): PipelineLayout {
        val pipelineLayoutCreateInfo = memScope.alloc<VkPipelineLayoutCreateInfo> {
            sType = VK_STRUCTURE_TYPE_PIPELINE_LAYOUT_CREATE_INFO
            createInfo()
        }
        val pipelineLayoutVar = memScope.alloc<VkPipelineLayoutVar>()
        vkCreatePipelineLayout!!(handle, pipelineLayoutCreateInfo.ptr, null, pipelineLayoutVar.ptr)
            .checkResult("Failed to create pipeline layout")
        return PipelineLayout(handle, pipelineLayoutVar.value!!)
    }

    /**
     * Create a new query pool.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCreateQueryPool.html">vkCreateQueryPool</a>
     */
    context(memScope: MemScope)
    fun createQueryPool(createInfo: VkQueryPoolCreateInfo.() -> Unit): QueryPool {
        val queryPoolCreateInfo = memScope.alloc<VkQueryPoolCreateInfo> {
            sType = VK_STRUCTURE_TYPE_QUERY_POOL_CREATE_INFO
            createInfo()
        }
        val queryPoolVar = memScope.alloc<VkQueryPoolVar>()
        vkCreateQueryPool!!(handle, queryPoolCreateInfo.ptr, null, queryPoolVar.ptr)
            .checkResult("Failed to create query pool")
        return QueryPool(handle, queryPoolVar.value!!)
    }

    /**
     * Create a new sampler.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCreateSampler.html">vkCreateSampler</a>
     */
    context(memScope: MemScope)
    fun createSampler(createInfo: VkSamplerCreateInfo.() -> Unit): Sampler {
        val samplerCreateInfo = memScope.alloc<VkSamplerCreateInfo> {
            sType = VK_STRUCTURE_TYPE_SAMPLER_CREATE_INFO
            createInfo()
        }
        val samplerVar = memScope.alloc<VkSamplerVar>()
        vkCreateSampler!!(handle, samplerCreateInfo.ptr, null, samplerVar.ptr)
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
        val shaderModuleCreateInfo = memScope.alloc<VkShaderModuleCreateInfo> {
            sType = VK_STRUCTURE_TYPE_SHADER_MODULE_CREATE_INFO
            createInfo()
        }
        val shaderModule = memScope.alloc<VkShaderModuleVar>()
        vkCreateShaderModule!!(handle, shaderModuleCreateInfo.ptr, null, shaderModule.ptr)
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
     * Update descriptor sets.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkUpdateDescriptorSets.html">vkUpdateDescriptorSets</a>
     */
    context(memScope: MemScope)
    fun updateDescriptorSets(writes: List<VkWriteDescriptorSet>, copies: List<VkCopyDescriptorSet> = emptyList()) {
        val writesArray = if (writes.isNotEmpty()) {
            memScope.allocArray<VkWriteDescriptorSet>(writes.size) { index ->
                writes[index]
            }
        } else {
            null
        }

        val copiesArray = if (copies.isNotEmpty()) {
            memScope.allocArray<VkCopyDescriptorSet>(copies.size) { index ->
                copies[index]
            }
        } else {
            null
        }

        vkUpdateDescriptorSets!!(
            handle,
            writes.size.toUInt(),
            writesArray,
            copies.size.toUInt(),
            copiesArray
        )
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
