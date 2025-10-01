package io.technoirlab.vulkan

import io.technoirlab.volk.VK_PIPELINE_BIND_POINT_GRAPHICS
import io.technoirlab.volk.VK_STRUCTURE_TYPE_BUFFER_MEMORY_BARRIER_2
import io.technoirlab.volk.VK_STRUCTURE_TYPE_COMMAND_BUFFER_BEGIN_INFO
import io.technoirlab.volk.VK_STRUCTURE_TYPE_DEPENDENCY_INFO
import io.technoirlab.volk.VK_STRUCTURE_TYPE_IMAGE_MEMORY_BARRIER_2
import io.technoirlab.volk.VK_STRUCTURE_TYPE_RENDERING_INFO
import io.technoirlab.volk.VK_WHOLE_SIZE
import io.technoirlab.volk.VkBufferMemoryBarrier2
import io.technoirlab.volk.VkBufferVar
import io.technoirlab.volk.VkCommandBuffer
import io.technoirlab.volk.VkCommandBufferBeginInfo
import io.technoirlab.volk.VkCompareOp
import io.technoirlab.volk.VkCullModeFlags
import io.technoirlab.volk.VkDependencyInfo
import io.technoirlab.volk.VkDeviceSize
import io.technoirlab.volk.VkDeviceSizeVar
import io.technoirlab.volk.VkFrontFace
import io.technoirlab.volk.VkImageMemoryBarrier2
import io.technoirlab.volk.VkIndexType
import io.technoirlab.volk.VkPipelineBindPoint
import io.technoirlab.volk.VkPolygonMode
import io.technoirlab.volk.VkPrimitiveTopology
import io.technoirlab.volk.VkRect2D
import io.technoirlab.volk.VkRenderingInfo
import io.technoirlab.volk.VkShaderStageFlags
import io.technoirlab.volk.VkStencilFaceFlags
import io.technoirlab.volk.VkStencilOp
import io.technoirlab.volk.VkViewport
import io.technoirlab.volk.vkBeginCommandBuffer
import io.technoirlab.volk.vkCmdBeginRendering
import io.technoirlab.volk.vkCmdBindDescriptorSets
import io.technoirlab.volk.vkCmdBindIndexBuffer
import io.technoirlab.volk.vkCmdBindIndexBuffer2
import io.technoirlab.volk.vkCmdBindPipeline
import io.technoirlab.volk.vkCmdBindVertexBuffers
import io.technoirlab.volk.vkCmdBindVertexBuffers2
import io.technoirlab.volk.vkCmdDraw
import io.technoirlab.volk.vkCmdDrawIndexed
import io.technoirlab.volk.vkCmdDrawIndexedIndirect
import io.technoirlab.volk.vkCmdDrawIndirect
import io.technoirlab.volk.vkCmdEndRendering
import io.technoirlab.volk.vkCmdExecuteCommands
import io.technoirlab.volk.vkCmdPipelineBarrier2
import io.technoirlab.volk.vkCmdPushConstants
import io.technoirlab.volk.vkCmdSetBlendConstants
import io.technoirlab.volk.vkCmdSetCullMode
import io.technoirlab.volk.vkCmdSetDepthBias
import io.technoirlab.volk.vkCmdSetDepthBiasEnable
import io.technoirlab.volk.vkCmdSetDepthBoundsTestEnable
import io.technoirlab.volk.vkCmdSetDepthCompareOp
import io.technoirlab.volk.vkCmdSetDepthTestEnable
import io.technoirlab.volk.vkCmdSetDepthWriteEnable
import io.technoirlab.volk.vkCmdSetFrontFace
import io.technoirlab.volk.vkCmdSetLineWidth
import io.technoirlab.volk.vkCmdSetPolygonModeEXT
import io.technoirlab.volk.vkCmdSetPrimitiveRestartEnable
import io.technoirlab.volk.vkCmdSetPrimitiveTopology
import io.technoirlab.volk.vkCmdSetRasterizerDiscardEnable
import io.technoirlab.volk.vkCmdSetScissor
import io.technoirlab.volk.vkCmdSetScissorWithCount
import io.technoirlab.volk.vkCmdSetStencilCompareMask
import io.technoirlab.volk.vkCmdSetStencilOp
import io.technoirlab.volk.vkCmdSetStencilReference
import io.technoirlab.volk.vkCmdSetStencilTestEnable
import io.technoirlab.volk.vkCmdSetStencilWriteMask
import io.technoirlab.volk.vkCmdSetViewport
import io.technoirlab.volk.vkCmdSetViewportWithCount
import io.technoirlab.volk.vkEndCommandBuffer
import io.technoirlab.volk.vkResetCommandBuffer
import kotlinx.cinterop.MemScope
import kotlinx.cinterop.UIntVar
import kotlinx.cinterop.ULongVar
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.alloc
import kotlinx.cinterop.allocArray
import kotlinx.cinterop.allocArrayOf
import kotlinx.cinterop.invoke
import kotlinx.cinterop.ptr
import kotlinx.cinterop.usePinned
import kotlinx.cinterop.value

/**
 * Wrapper for [VkCommandBuffer].
 *
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/VkCommandBuffer.html">VkCommandBuffer Manual Page</a>
 */
@Suppress("LongParameterList")
class CommandBuffer(val handle: VkCommandBuffer) {
    /**
     * Start recording the command buffer.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkBeginCommandBuffer.html">vkBeginCommandBuffer Manual Page</a>
     */
    context(memScope: MemScope)
    fun begin(beginInfo: VkCommandBufferBeginInfo.() -> Unit = {}) {
        val beginInfo = memScope.alloc<VkCommandBufferBeginInfo> {
            sType = VK_STRUCTURE_TYPE_COMMAND_BUFFER_BEGIN_INFO
            beginInfo()
        }
        vkBeginCommandBuffer!!(handle, beginInfo.ptr)
            .checkResult("Failed to begin command buffer")
    }

    /**
     * Begin a dynamic render pass instance.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdBeginRendering.html">vkCmdBeginRendering Manual Page</a>
     */
    context(memScope: MemScope)
    fun beginRendering(renderingInfo: VkRenderingInfo.() -> Unit) {
        val renderingInfo = memScope.alloc<VkRenderingInfo> {
            sType = VK_STRUCTURE_TYPE_RENDERING_INFO
            renderingInfo()
        }
        vkCmdBeginRendering!!(handle, renderingInfo.ptr)
    }

    /**
     * Bind descriptor sets to the command buffer.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdBindDescriptorSets.html">vkCmdBindDescriptorSets Manual Page</a>
     */
    context(memScope: MemScope)
    fun bindDescriptorSets(
        pipelineBindPoint: VkPipelineBindPoint = VK_PIPELINE_BIND_POINT_GRAPHICS,
        layout: PipelineLayout,
        firstSet: UInt,
        descriptorSets: List<DescriptorSet>,
        dynamicOffsets: List<UInt> = emptyList()
    ) {
        val descriptorSetHandles = memScope.allocArrayOf(descriptorSets.map { it.handle })
        val dynamicOffsetArray = if (dynamicOffsets.isNotEmpty()) {
            memScope.allocArray<UIntVar>(dynamicOffsets.size) { index ->
                value = dynamicOffsets[index]
            }
        } else {
            null
        }

        vkCmdBindDescriptorSets!!(
            handle,
            pipelineBindPoint,
            layout.handle,
            firstSet,
            descriptorSets.size.toUInt(),
            descriptorSetHandles,
            dynamicOffsets.size.toUInt(),
            dynamicOffsetArray
        )
    }

    /**
     * Bind an index buffer to the command buffer.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdBindIndexBuffer.html">vkCmdBindIndexBuffer Manual Page</a>
     */
    fun bindIndexBuffer(indexBuffer: Buffer, indexType: VkIndexType, size: ULong = VK_WHOLE_SIZE) {
        vkCmdBindIndexBuffer!!(handle, indexBuffer.handle, size, indexType)
    }

    /**
     * Bind an index buffer to the command buffer.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdBindIndexBuffer2.html">vkCmdBindIndexBuffer2 Manual Page</a>
     */
    fun bindIndexBuffer(indexBuffer: Buffer, indexType: VkIndexType, offset: ULong = 0u, size: ULong = VK_WHOLE_SIZE) {
        vkCmdBindIndexBuffer2!!(handle, indexBuffer.handle, offset, size, indexType)
    }

    /**
     * Bind a pipeline to the command buffer.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdBindPipeline.html">vkCmdBindPipeline Manual Page</a>
     */
    fun bindPipeline(pipeline: Pipeline, pipelineBindPoint: VkPipelineBindPoint = VK_PIPELINE_BIND_POINT_GRAPHICS) {
        vkCmdBindPipeline!!(handle, pipelineBindPoint, pipeline.handle)
    }

    /**
     * Bind a vertex buffer to the command buffer.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdBindVertexBuffers.html">vkCmdBindVertexBuffers Manual Page</a>
     */
    context(memScope: MemScope)
    fun bindVertexBuffer(vertexBuffer: Buffer, offset: VkDeviceSize = 0u, bindingIndex: UInt = 0u) {
        val vertexBufferVar = memScope.alloc<VkBufferVar> { value = vertexBuffer.handle }
        val offsetVar = memScope.alloc<VkDeviceSizeVar> { value = offset }
        vkCmdBindVertexBuffers!!(handle, bindingIndex, 1u, vertexBufferVar.ptr, offsetVar.ptr)
    }

    /**
     * Bind vertex buffers to the command buffer.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdBindVertexBuffers.html">vkCmdBindVertexBuffers Manual Page</a>
     */
    context(memScope: MemScope)
    fun bindVertexBuffers(vertexBuffers: List<Buffer>, offsets: List<ULong>, firstBinding: UInt = 0u) {
        val vertexBufferHandles = memScope.allocArrayOf(vertexBuffers.map { it.handle })
        val offsetsArray = memScope.allocArray<ULongVar>(offsets.size) { value = offsets[it] }
        vkCmdBindVertexBuffers!!(handle, firstBinding, vertexBuffers.size.toUInt(), vertexBufferHandles, offsetsArray)
    }

    /**
     * Bind vertex buffers to the command buffer.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdBindVertexBuffers2.html">vkCmdBindVertexBuffers2 Manual Page</a>
     */
    context(memScope: MemScope)
    fun bindVertexBuffers2(vertexBuffers: List<Buffer>, offsets: List<ULong>, firstBinding: UInt = 0u) {
        val vertexBufferHandles = memScope.allocArrayOf(vertexBuffers.map { it.handle })
        val offsetsArray = memScope.allocArray<ULongVar>(offsets.size) { value = offsets[it] }
        vkCmdBindVertexBuffers2!!(handle, firstBinding, vertexBuffers.size.toUInt(), vertexBufferHandles, null, null, null)
    }

    /**
     * Insert a pipeline barrier for buffer memory.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdPipelineBarrier2.html">vkCmdPipelineBarrier2 Manual Page</a>
     */
    context(memScope: MemScope)
    fun bufferMemoryBarrier(barrierInfo: VkBufferMemoryBarrier2.() -> Unit) {
        val bufferMemoryBarrier = memScope.alloc<VkBufferMemoryBarrier2> {
            sType = VK_STRUCTURE_TYPE_BUFFER_MEMORY_BARRIER_2
            barrierInfo()
        }
        pipelineBarrier {
            bufferMemoryBarrierCount = 1u
            pBufferMemoryBarriers = bufferMemoryBarrier.ptr
        }
    }

    /**
     * Finish recording the command buffer.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkEndCommandBuffer.html">vkEndCommandBuffer Manual Page</a>
     */
    fun end() {
        vkEndCommandBuffer!!(handle)
            .checkResult("Failed to end command buffer")
    }

    /**
     * End a dynamic render pass instance.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdEndRendering.html">vkCmdEndRendering Manual Page</a>
     */
    fun endRendering() {
        vkCmdEndRendering!!(handle)
    }

    /**
     * Execute secondary command buffers from the primary command buffer.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdExecuteCommands.html">vkCmdExecuteCommands Manual Page</a>
     */
    context(memScope: MemScope)
    fun executeCommands(commandBuffers: List<CommandBuffer>) {
        val commandBufferHandles = memScope.allocArrayOf(commandBuffers.map { it.handle })
        vkCmdExecuteCommands!!(handle, commandBuffers.size.toUInt(), commandBufferHandles)
    }

    /**
     * Draw primitives.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdDraw.html">vkCmdDraw Manual Page</a>
     */
    fun draw(vertexCount: UInt, instanceCount: UInt = 1u, firstVertex: UInt = 0u, firstInstance: UInt = 0u) {
        vkCmdDraw!!(handle, vertexCount, instanceCount, firstVertex, firstInstance)
    }

    /**
     * Draw primitives with indexed vertices.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdDrawIndexed.html">vkCmdDrawIndexed Manual Page</a>
     */
    fun drawIndexed(indexCount: UInt, instanceCount: UInt = 1u, firstIndex: UInt = 0u, vertexOffset: Int = 0, firstInstance: UInt = 0u) {
        vkCmdDrawIndexed!!(handle, indexCount, instanceCount, firstIndex, vertexOffset, firstInstance)
    }

    /**
     * Draw primitives indirectly.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdDrawIndirect.html">vkCmdDrawIndirect Manual Page</a>
     */
    fun drawIndirect(buffer: Buffer, offset: ULong, drawCount: UInt, stride: UInt) {
        vkCmdDrawIndirect!!(handle, buffer.handle, offset, drawCount, stride)
    }

    /**
     * Draw primitives with indexed vertices indirectly.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdDrawIndexedIndirect.html">vkCmdDrawIndexedIndirect Manual Page</a>
     */
    fun drawIndexedIndirect(buffer: Buffer, offset: ULong, drawCount: UInt, stride: UInt) {
        vkCmdDrawIndexedIndirect!!(handle, buffer.handle, offset, drawCount, stride)
    }

    /**
     * Insert a pipeline barrier for image memory.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdPipelineBarrier2.html">vkCmdPipelineBarrier2 Manual Page</a>
     */
    context(memScope: MemScope)
    fun imageMemoryBarrier(barrierInfo: VkImageMemoryBarrier2.() -> Unit) {
        val imageMemoryBarrier = memScope.alloc<VkImageMemoryBarrier2> {
            sType = VK_STRUCTURE_TYPE_IMAGE_MEMORY_BARRIER_2
            barrierInfo()
        }
        pipelineBarrier {
            imageMemoryBarrierCount = 1u
            pImageMemoryBarriers = imageMemoryBarrier.ptr
        }
    }

    /**
     * Insert a pipeline barrier.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdPipelineBarrier2.html">vkCmdPipelineBarrier2 Manual Page</a>
     */
    context(memScope: MemScope)
    fun pipelineBarrier(dependencyInfo: VkDependencyInfo.() -> Unit) {
        val dependencyInfo = memScope.alloc<VkDependencyInfo> {
            sType = VK_STRUCTURE_TYPE_DEPENDENCY_INFO
            dependencyInfo()
        }
        vkCmdPipelineBarrier2!!(handle, dependencyInfo.ptr)
    }

    /**
     * Update the values of push constants.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdPushConstants.html">vkCmdPushConstants Manual Page</a>
     */
    fun pushConstants(
        layout: PipelineLayout,
        stageFlags: VkShaderStageFlags,
        values: ByteArray,
        offset: UInt = 0u
    ) {
        values.usePinned {
            vkCmdPushConstants!!(
                handle,
                layout.handle,
                stageFlags,
                offset,
                values.size.toUInt(),
                it.addressOf(0)
            )
        }
    }

    /**
     * Reset the command buffer to the initial state.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkResetCommandBuffer.html">vkResetCommandBuffer Manual Page</a>
     */
    fun reset(flags: UInt = 0u) {
        vkResetCommandBuffer!!(handle, flags)
            .checkResult("Failed to reset command buffer")
    }

    /**
     * Set blend constants dynamically for the command buffer.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdSetBlendConstants.html">vkCmdSetBlendConstants Manual Page</a>
     */
    fun setBlendConstants(r: Float, g: Float, b: Float, a: Float) {
        val blendConstants = floatArrayOf(r, g, b, a)
        blendConstants.usePinned {
            vkCmdSetBlendConstants!!(handle, it.addressOf(0))
        }
    }

    /**
     * Set cull mode dynamically for the command buffer.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdSetCullMode.html">vkCmdSetCullMode Manual Page</a>
     */
    fun setCullMode(cullMode: VkCullModeFlags) {
        vkCmdSetCullMode!!(handle, cullMode)
    }

    /**
     * Set depth bias factors and clamp dynamically for the command buffer.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdSetDepthBias.html">vkCmdSetDepthBias Manual Page</a>
     */
    fun setDepthBias(constantFactor: Float, clamp: Float, slopeFactor: Float) {
        vkCmdSetDepthBias!!(handle, constantFactor, clamp, slopeFactor)
    }

    /**
     * Control whether to bias fragment depth values dynamically for the command buffer.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdSetDepthBiasEnable.html">vkCmdSetDepthBiasEnable Manual Page</a>
     */
    fun setDepthBiasEnable(enable: Boolean) {
        vkCmdSetDepthBiasEnable!!(handle, enable.toVkBool32())
    }

    /**
     * Enable or disable depth bounds testing dynamically for the command buffer.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdSetDepthBoundsTestEnable.html">vkCmdSetDepthBoundsTestEnable Manual Page</a>
     */
    fun setDepthBoundsTestEnable(enable: Boolean) {
        vkCmdSetDepthBoundsTestEnable!!(handle, enable.toVkBool32())
    }

    /**
     * Enable or disable depth testing dynamically for the command buffer.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdSetDepthTestEnable.html">vkCmdSetDepthTestEnable Manual Page</a>
     */
    fun setDepthTestEnable(enable: Boolean) {
        vkCmdSetDepthTestEnable!!(handle, enable.toVkBool32())
    }

    /**
     * Enable or disable writes to the depth buffer dynamically for the command buffer.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdSetDepthWriteEnable.html">vkCmdSetDepthWriteEnable Manual Page</a>
     */
    fun setDepthWriteEnable(enable: Boolean) {
        vkCmdSetDepthWriteEnable!!(handle, enable.toVkBool32())
    }

    /**
     * Set depth comparison operator dynamically for the command buffer.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdSetDepthCompareOp.html">vkCmdSetDepthCompareOp Manual Page</a>
     */
    fun setDepthCompareOp(compareOp: VkCompareOp) {
        vkCmdSetDepthCompareOp!!(handle, compareOp)
    }

    /**
     * Set front face orientation dynamically for the command buffer.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdSetFrontFace.html">vkCmdSetFrontFace Manual Page</a>
     */
    fun setFrontFace(frontFace: VkFrontFace) {
        vkCmdSetFrontFace!!(handle, frontFace)
    }

    /**
     * Set line width dynamically for the command buffer.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdSetLineWidth.html">vkCmdSetLineWidth Manual Page</a>
     */
    fun setLineWidth(lineWidth: Float) {
        vkCmdSetLineWidth!!(handle, lineWidth)
    }

    /**
     * Set polygon mode dynamically for the command buffer.
     *
     * Requires `VK_EXT_extended_dynamic_state3` or `VK_EXT_shader_object` extension.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdSetPolygonModeEXT.html">vkCmdSetPolygonModeEXT Manual Page</a>
     */
    fun setPolygonMode(polygonMode: VkPolygonMode) {
        vkCmdSetPolygonModeEXT!!(handle, polygonMode)
    }

    /**
     * Set primitive assembly restart state dynamically for the command buffer.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdSetPrimitiveRestartEnable.html">vkCmdSetPrimitiveRestartEnable Manual Page</a>
     */
    fun setPrimitiveRestartEnable(enable: Boolean) {
        vkCmdSetPrimitiveRestartEnable!!(handle, enable.toVkBool32())
    }

    /**
     * Set primitive topology dynamically for the command buffer.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdSetPrimitiveTopology.html">vkCmdSetPrimitiveTopology Manual Page</a>
     */
    fun setPrimitiveTopology(topology: VkPrimitiveTopology) {
        vkCmdSetPrimitiveTopology!!(handle, topology)
    }

    /**
     * Enable or disable rasterizer discard dynamically for the command buffer.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdSetRasterizerDiscardEnable.html">vkCmdSetRasterizerDiscardEnable Manual Page</a>
     */
    fun setRasterizerDiscardEnable(enable: Boolean) {
        vkCmdSetRasterizerDiscardEnable!!(handle, enable.toVkBool32())
    }

    /**
     * Set scissor rectangles dynamically for the command buffer.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdSetScissor.html">vkCmdSetScissor Manual Page</a>
     */
    context(memScope: MemScope)
    fun setScissor(scissor: VkRect2D.() -> Unit) {
        val scissor = memScope.alloc<VkRect2D> { scissor() }
        vkCmdSetScissor!!(handle, 0u, 1u, scissor.ptr)
    }

    /**
     *
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdSetScissorWithCount.html">vkCmdSetScissorWithCount Manual Page</a>
     */
    context(memScope: MemScope)
    fun setScissorWithCount(count: UInt, scissor: VkRect2D.(UInt) -> Unit) {
        val scissors = memScope.allocArray<VkRect2D>(count.toLong()) { scissor(it.toUInt()) }
        vkCmdSetScissorWithCount!!(handle, count, scissors)
    }

    /**
     * Set stencil test compare mask dynamically for the command buffer.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdSetStencilCompareMask.html">vkCmdSetStencilCompareMask Manual Page</a>
     */
    fun setStencilCompareMask(faceMask: VkStencilFaceFlags, compareMask: UInt) {
        vkCmdSetStencilCompareMask!!(handle, faceMask, compareMask)
    }

    /**
     * Set stencil test actions dynamically for the command buffer.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdSetStencilOp.html">vkCmdSetStencilOp Manual Page</a>
     */
    fun setStencilOp(
        faceMask: VkStencilFaceFlags,
        failOp: VkStencilOp,
        passOp: VkStencilOp,
        depthFailOp: VkStencilOp,
        compareOp: VkCompareOp
    ) {
        vkCmdSetStencilOp!!(handle, faceMask, failOp, passOp, depthFailOp, compareOp)
    }

    /**
     * Set stencil reference value dynamically for the command buffer.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdSetStencilReference.html">vkCmdSetStencilReference Manual Page</a>
     */
    fun setStencilReference(faceMask: VkStencilFaceFlags, reference: UInt) {
        vkCmdSetStencilReference!!(handle, faceMask, reference)
    }

    /**
     * Enable or disable stencil test dynamically for the command buffer.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdSetStencilTestEnable.html">vkCmdSetStencilTestEnable Manual Page</a>
     */
    fun setStencilTestEnable(enable: Boolean) {
        vkCmdSetStencilTestEnable!!(handle, enable.toVkBool32())
    }

    /**
     * Set stencil write mask dynamically for the command buffer.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdSetStencilWriteMask.html">vkCmdSetStencilWriteMask Manual Page</a>
     */
    fun setStencilWriteMask(faceMask: VkStencilFaceFlags, writeMask: UInt) {
        vkCmdSetStencilWriteMask!!(handle, faceMask, writeMask)
    }

    /**
     * Set viewport transformation parameters dynamically for the command buffer.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdSetViewport.html">vkCmdSetViewport Manual Page</a>
     */
    context(memScope: MemScope)
    fun setViewport(viewport: VkViewport.() -> Unit) {
        val vp = memScope.alloc<VkViewport> { viewport() }
        vkCmdSetViewport!!(handle, 0u, 1u, vp.ptr)
    }

    /**
     * Set the viewport count and viewports dynamically for the command buffer
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdSetViewportWithCount.html">vkCmdSetViewportWithCount Manual Page</a>
     */
    context(memScope: MemScope)
    fun setViewportWithCount(count: UInt, viewport: VkViewport.(UInt) -> Unit) {
        val viewports = memScope.allocArray<VkViewport>(count.toLong()) { viewport(it.toUInt()) }
        vkCmdSetViewportWithCount!!(handle, count, viewports)
    }
}
