package vulkan.kotlin

import kotlinx.cinterop.MemScope
import kotlinx.cinterop.UIntVar
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.alloc
import kotlinx.cinterop.allocArray
import kotlinx.cinterop.invoke
import kotlinx.cinterop.ptr
import kotlinx.cinterop.usePinned
import kotlinx.cinterop.value
import volk.VK_PIPELINE_BIND_POINT_GRAPHICS
import volk.VK_STRUCTURE_TYPE_BUFFER_MEMORY_BARRIER_2
import volk.VK_STRUCTURE_TYPE_COMMAND_BUFFER_BEGIN_INFO
import volk.VK_STRUCTURE_TYPE_DEPENDENCY_INFO
import volk.VK_STRUCTURE_TYPE_IMAGE_MEMORY_BARRIER_2
import volk.VK_STRUCTURE_TYPE_RENDERING_INFO
import volk.VK_WHOLE_SIZE
import volk.VkBufferMemoryBarrier2
import volk.VkBufferVar
import volk.VkCommandBuffer
import volk.VkCommandBufferBeginInfo
import volk.VkCompareOp
import volk.VkCullModeFlags
import volk.VkDependencyInfo
import volk.VkDescriptorSet
import volk.VkDescriptorSetVar
import volk.VkExtent2D
import volk.VkFrontFace
import volk.VkImageMemoryBarrier2
import volk.VkIndexType
import volk.VkOffset2D
import volk.VkPipelineBindPoint
import volk.VkPrimitiveTopology
import volk.VkRect2D
import volk.VkRenderingInfo
import volk.VkShaderStageFlags
import volk.VkStencilFaceFlags
import volk.VkStencilOp
import volk.VkViewport
import volk.vkBeginCommandBuffer
import volk.vkCmdBeginRendering
import volk.vkCmdBindDescriptorSets
import volk.vkCmdBindIndexBuffer2
import volk.vkCmdBindPipeline
import volk.vkCmdBindVertexBuffers2
import volk.vkCmdDraw
import volk.vkCmdDrawIndexed
import volk.vkCmdDrawIndexedIndirect
import volk.vkCmdDrawIndirect
import volk.vkCmdEndRendering
import volk.vkCmdPipelineBarrier2
import volk.vkCmdPushConstants
import volk.vkCmdSetBlendConstants
import volk.vkCmdSetCullMode
import volk.vkCmdSetDepthBias
import volk.vkCmdSetDepthBiasEnable
import volk.vkCmdSetDepthBoundsTestEnable
import volk.vkCmdSetDepthCompareOp
import volk.vkCmdSetDepthTestEnable
import volk.vkCmdSetDepthWriteEnable
import volk.vkCmdSetFrontFace
import volk.vkCmdSetLineWidth
import volk.vkCmdSetPrimitiveRestartEnable
import volk.vkCmdSetPrimitiveTopology
import volk.vkCmdSetRasterizerDiscardEnable
import volk.vkCmdSetScissor
import volk.vkCmdSetStencilCompareMask
import volk.vkCmdSetStencilOp
import volk.vkCmdSetStencilReference
import volk.vkCmdSetStencilTestEnable
import volk.vkCmdSetStencilWriteMask
import volk.vkCmdSetViewport
import volk.vkEndCommandBuffer
import volk.vkResetCommandBuffer

@Suppress("LongParameterList")
class CommandBuffer(val handle: VkCommandBuffer) {
    /**
     * Start recording the command buffer.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkBeginCommandBuffer.html">vkBeginCommandBuffer</a>
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
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdBeginRendering.html">vkCmdBeginRendering</a>
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
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdBindDescriptorSets.html">vkCmdBindDescriptorSets</a>
     */
    context(memScope: MemScope)
    fun bindDescriptorSets(
        pipelineBindPoint: VkPipelineBindPoint = VK_PIPELINE_BIND_POINT_GRAPHICS,
        layout: PipelineLayout,
        firstSet: UInt,
        descriptorSets: List<VkDescriptorSet>,
        dynamicOffsets: List<UInt> = emptyList()
    ) {
        val descriptorSetArray = memScope.allocArray<VkDescriptorSetVar>(descriptorSets.size) { index ->
            value = descriptorSets[index]
        }
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
            descriptorSetArray,
            dynamicOffsets.size.toUInt(),
            dynamicOffsetArray
        )
    }

    /**
     * Bind an index buffer to the command buffer.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdBindIndexBuffer2.html">vkCmdBindIndexBuffer2</a>
     */
    fun bindIndexBuffer(indexBuffer: Buffer, indexType: VkIndexType) {
        vkCmdBindIndexBuffer2!!(handle, indexBuffer.handle, 0u, VK_WHOLE_SIZE, indexType)
    }

    /**
     * Bind a pipeline to the command buffer.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdBindPipeline.html">vkCmdBindPipeline</a>
     */
    fun bindPipeline(pipeline: Pipeline, pipelineBindPoint: VkPipelineBindPoint = VK_PIPELINE_BIND_POINT_GRAPHICS) {
        vkCmdBindPipeline!!(handle, pipelineBindPoint, pipeline.handle)
    }

    /**
     * Bind vertex buffers to the command buffer.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdBindVertexBuffers2.html">vkCmdBindVertexBuffers2</a>
     */
    context(memScope: MemScope)
    fun bindVertexBuffers(vertexBuffers: List<Buffer>) {
        val buffers = memScope.allocArray<VkBufferVar>(vertexBuffers.size) {
            value = vertexBuffers[it].handle
        }
        vkCmdBindVertexBuffers2!!(handle, 0u, vertexBuffers.size.toUInt(), buffers, null, null, null)
    }

    /**
     * Insert a pipeline barrier for buffer memory.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdPipelineBarrier2.html">vkCmdPipelineBarrier2</a>
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
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkEndCommandBuffer.html">vkEndCommandBuffer</a>
     */
    fun end() {
        vkEndCommandBuffer!!(handle)
            .checkResult("Failed to end command buffer")
    }

    /**
     * End a dynamic render pass instance.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdEndRendering.html">vkCmdEndRendering</a>
     */
    fun endRendering() {
        vkCmdEndRendering!!(handle)
    }

    /**
     * Draw primitives.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdDraw.html">vkCmdDraw</a>
     */
    fun draw(vertexCount: UInt, instanceCount: UInt, firstVertex: UInt, firstInstance: UInt) {
        vkCmdDraw!!(handle, vertexCount, instanceCount, firstVertex, firstInstance)
    }

    /**
     * Draw primitives with indexed vertices.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdDrawIndexed.html">vkCmdDrawIndexed</a>
     */
    fun drawIndexed(indexCount: UInt, instanceCount: UInt, firstIndex: UInt, vertexOffset: Int, firstInstance: UInt) {
        vkCmdDrawIndexed!!(handle, indexCount, instanceCount, firstIndex, vertexOffset, firstInstance)
    }

    /**
     * Draw primitives indirectly.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdDrawIndirect.html">vkCmdDrawIndirect</a>
     */
    fun drawIndirect(buffer: Buffer, offset: ULong, drawCount: UInt, stride: UInt) {
        vkCmdDrawIndirect!!(handle, buffer.handle, offset, drawCount, stride)
    }

    /**
     * Draw primitives with indexed vertices indirectly.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdDrawIndexedIndirect.html">vkCmdDrawIndexedIndirect</a>
     */
    fun drawIndexedIndirect(buffer: Buffer, offset: ULong, drawCount: UInt, stride: UInt) {
        vkCmdDrawIndexedIndirect!!(handle, buffer.handle, offset, drawCount, stride)
    }

    /**
     * Insert a pipeline barrier for image memory.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdPipelineBarrier2.html">vkCmdPipelineBarrier2</a>
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
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdPipelineBarrier2.html">vkCmdPipelineBarrier2</a>
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
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdPushConstants.html">vkCmdPushConstants</a>
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
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkResetCommandBuffer.html">vkResetCommandBuffer</a>
     */
    fun reset(flags: UInt = 0u) {
        vkResetCommandBuffer!!(handle, flags)
            .checkResult("Failed to reset command buffer")
    }

    /**
     * Set blend constants dynamically for the command buffer.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdSetBlendConstants.html">vkCmdSetBlendConstants</a>
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
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdSetCullMode.html">vkCmdSetCullMode</a>
     */
    fun setCullMode(cullMode: VkCullModeFlags) {
        vkCmdSetCullMode!!(handle, cullMode)
    }

    /**
     * Set depth bias factors and clamp dynamically for the command buffer.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdSetDepthBias.html">vkCmdSetDepthBias</a>
     */
    fun setDepthBias(constantFactor: Float, clamp: Float, slopeFactor: Float) {
        vkCmdSetDepthBias!!(handle, constantFactor, clamp, slopeFactor)
    }

    /**
     * Control whether to bias fragment depth values dynamically for the command buffer.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdSetDepthBiasEnable.html">vkCmdSetDepthBiasEnable</a>
     */
    fun setDepthBiasEnable(enable: Boolean) {
        vkCmdSetDepthBiasEnable!!(handle, enable.toVkBool32())
    }

    /**
     * Enable or disable depth bounds testing dynamically for the command buffer.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdSetDepthBoundsTestEnable.html">vkCmdSetDepthBoundsTestEnable</a>
     */
    fun setDepthBoundsTestEnable(enable: Boolean) {
        vkCmdSetDepthBoundsTestEnable!!(handle, enable.toVkBool32())
    }

    /**
     * Enable or disable depth testing dynamically for the command buffer.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdSetDepthTestEnable.html">vkCmdSetDepthTestEnable</a>
     */
    fun setDepthTestEnable(enable: Boolean) {
        vkCmdSetDepthTestEnable!!(handle, enable.toVkBool32())
    }

    /**
     * Enable or disable writes to the depth buffer dynamically for the command buffer.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdSetDepthWriteEnable.html">vkCmdSetDepthWriteEnable</a>
     */
    fun setDepthWriteEnable(enable: Boolean) {
        vkCmdSetDepthWriteEnable!!(handle, enable.toVkBool32())
    }

    /**
     * Set depth comparison operator dynamically for the command buffer.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdSetDepthCompareOp.html">vkCmdSetDepthCompareOp</a>
     */
    fun setDepthCompareOp(compareOp: VkCompareOp) {
        vkCmdSetDepthCompareOp!!(handle, compareOp)
    }

    /**
     * Set front face orientation dynamically for the command buffer.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdSetFrontFace.html">vkCmdSetFrontFace</a>
     */
    fun setFrontFace(frontFace: VkFrontFace) {
        vkCmdSetFrontFace!!(handle, frontFace)
    }

    /**
     * Set line width dynamically for the command buffer.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdSetLineWidth.html">vkCmdSetLineWidth</a>
     */
    fun setLineWidth(lineWidth: Float) {
        vkCmdSetLineWidth!!(handle, lineWidth)
    }

    /**
     * Set primitive assembly restart state dynamically for the command buffer.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdSetPrimitiveRestartEnable.html">vkCmdSetPrimitiveRestartEnable</a>
     */
    fun setPrimitiveRestartEnable(enable: Boolean) {
        vkCmdSetPrimitiveRestartEnable!!(handle, enable.toVkBool32())
    }

    /**
     * Set primitive topology dynamically for the command buffer.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdSetPrimitiveTopology.html">vkCmdSetPrimitiveTopology</a>
     */
    fun setPrimitiveTopology(topology: VkPrimitiveTopology) {
        vkCmdSetPrimitiveTopology!!(handle, topology)
    }

    /**
     * Enable or disable rasterizer discard dynamically for the command buffer.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdSetRasterizerDiscardEnable.html">vkCmdSetRasterizerDiscardEnable</a>
     */
    fun setRasterizerDiscardEnable(enable: Boolean) {
        vkCmdSetRasterizerDiscardEnable!!(handle, enable.toVkBool32())
    }

    /**
     * Set scissor rectangles dynamically for the command buffer.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdSetScissor.html">vkCmdSetScissor</a>
     */
    context(memScope: MemScope)
    fun setScissor(extent: VkExtent2D, offset: VkOffset2D) {
        val scissor = memScope.alloc<VkRect2D> {
            this.offset.x = offset.x
            this.offset.y = offset.y
            this.extent.width = extent.width
            this.extent.height = extent.height
        }
        vkCmdSetScissor!!(handle, 0u, 1u, scissor.ptr)
    }

    /**
     * Set stencil test compare mask dynamically for the command buffer.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdSetStencilCompareMask.html">vkCmdSetStencilCompareMask</a>
     */
    fun setStencilCompareMask(faceMask: VkStencilFaceFlags, compareMask: UInt) {
        vkCmdSetStencilCompareMask!!(handle, faceMask, compareMask)
    }

    /**
     * Set stencil test actions dynamically for the command buffer.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdSetStencilOp.html">vkCmdSetStencilOp</a>
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
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdSetStencilReference.html">vkCmdSetStencilReference</a>
     */
    fun setStencilReference(faceMask: VkStencilFaceFlags, reference: UInt) {
        vkCmdSetStencilReference!!(handle, faceMask, reference)
    }

    /**
     * Enable or disable stencil test dynamically for the command buffer.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdSetStencilTestEnable.html">vkCmdSetStencilTestEnable</a>
     */
    fun setStencilTestEnable(enable: Boolean) {
        vkCmdSetStencilTestEnable!!(handle, enable.toVkBool32())
    }

    /**
     * Set stencil write mask dynamically for the command buffer.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdSetStencilWriteMask.html">vkCmdSetStencilWriteMask</a>
     */
    fun setStencilWriteMask(faceMask: VkStencilFaceFlags, writeMask: UInt) {
        vkCmdSetStencilWriteMask!!(handle, faceMask, writeMask)
    }

    /**
     * Set viewport transformation parameters dynamically for the command buffer.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdSetViewport.html">vkCmdSetViewport</a>
     */
    context(memScope: MemScope)
    fun setViewport(viewport: VkViewport.() -> Unit) {
        val vp = memScope.alloc<VkViewport> { viewport() }
        vkCmdSetViewport!!(handle, 0u, 1u, vp.ptr)
    }
}
