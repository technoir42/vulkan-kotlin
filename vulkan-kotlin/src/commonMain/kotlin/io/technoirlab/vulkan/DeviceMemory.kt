package io.technoirlab.vulkan

import io.technoirlab.volk.VkDevice
import io.technoirlab.volk.VkDeviceMemory
import io.technoirlab.volk.vkFreeMemory
import io.technoirlab.volk.vkMapMemory
import io.technoirlab.volk.vkUnmapMemory
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.CPointed
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.CPointerVar
import kotlinx.cinterop.MemScope
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.alloc
import kotlinx.cinterop.convert
import kotlinx.cinterop.invoke
import kotlinx.cinterop.plus
import kotlinx.cinterop.ptr
import kotlinx.cinterop.reinterpret
import kotlinx.cinterop.usePinned
import kotlinx.cinterop.value
import kotlinx.io.Source
import platform.posix.memcpy
import kotlin.math.min

/**
 * Wrapper for [VkDeviceMemory].
 *
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/VkDeviceMemory.html">VkDeviceMemory Manual Page</a>
 */
class DeviceMemory(
    private val device: VkDevice,
    val handle: VkDeviceMemory,
    val size: ULong
) : AutoCloseable {

    /**
     * Copy data from a source to the device memory.
     */
    context(memScope: MemScope)
    fun copyData(source: Source, expectedSize: ULong, offset: ULong = 0u) {
        val mappedPtr = map(expectedSize, offset).reinterpret<ByteVar>()
        try {
            val buffer = ByteArray(READ_BUFFER_SIZE)
            var totalRead: ULong = 0u
            while (totalRead < expectedSize) {
                val remaining = expectedSize - totalRead
                val toRead = min(READ_BUFFER_SIZE.toULong(), remaining)
                val read = source.readAtMostTo(buffer, 0, toRead.convert())
                if (read <= 0) break
                buffer.usePinned { pinned ->
                    val destPtr = mappedPtr + totalRead.toLong()
                    memcpy(destPtr, pinned.addressOf(0), read.convert())
                }
                totalRead += read.toULong()
            }
            check(totalRead == expectedSize) {
                "Not enough data in source: expected $expectedSize bytes, but read $totalRead bytes"
            }
        } finally {
            unmap()
        }
    }

    /**
     * Map the memory into application address space.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkMapMemory.html">vkMapMemory Manual Page</a>
     */
    context(memScope: MemScope)
    fun map(size: ULong, offset: ULong = 0u): CPointer<out CPointed> {
        require(size > 0u) { "Size must be greater than 0" }
        require(offset + size <= this.size) { "Offset $offset + $size exceeds total memory size ${this.size}" }

        val mappedPtr = memScope.alloc<CPointerVar<out CPointed>>()
        vkMapMemory!!(device, handle, offset, size, 0u, mappedPtr.ptr)
            .checkResult("Failed to map memory")
        return mappedPtr.value!!
    }

    /**
     * Unmap the previously mapped memory.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkUnmapMemory.html">vkUnmapMemory Manual Page</a>
     */
    context(memScope: MemScope)
    fun unmap() {
        vkUnmapMemory!!(device, handle)
    }

    /**
     * Free the device memory.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkFreeMemory.html">vkFreeMemory Manual Page</a>
     */
    override fun close() {
        vkFreeMemory!!(device, handle, null)
    }

    companion object {
        private const val READ_BUFFER_SIZE = 64 * 1024
    }
}
