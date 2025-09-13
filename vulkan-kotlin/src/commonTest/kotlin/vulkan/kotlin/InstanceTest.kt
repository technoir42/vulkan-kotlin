package vulkan.kotlin

import kotlinx.cinterop.MemScope
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.toCStringArray
import volk.VK_DEBUG_UTILS_MESSAGE_SEVERITY_ERROR_BIT_EXT
import volk.VK_DEBUG_UTILS_MESSAGE_TYPE_GENERAL_BIT_EXT
import volk.VK_EXT_DEBUG_UTILS_EXTENSION_NAME
import volk.VK_INSTANCE_CREATE_ENUMERATE_PORTABILITY_BIT_KHR
import volk.VK_KHR_PORTABILITY_ENUMERATION_EXTENSION_NAME
import kotlin.experimental.ExperimentalNativeApi
import kotlin.test.Test
import kotlin.test.assertTrue

class InstanceTest {
    @Test
    fun `enumerate physical devices`() = withInstance {
        val physicalDevices = it.enumeratePhysicalDevices().toList()

        assertTrue(physicalDevices.isNotEmpty())
    }

    @Test
    fun `create debug utils messenger`() = withInstance {
        val messenger = it.createDebugUtilsMessenger {
            messageSeverity = VK_DEBUG_UTILS_MESSAGE_SEVERITY_ERROR_BIT_EXT
            messageType = VK_DEBUG_UTILS_MESSAGE_TYPE_GENERAL_BIT_EXT
        }

        messenger.close()
    }

    private fun withInstance(block: MemScope.(Instance) -> Unit) =
        memScoped {
            Context().use { context ->
                context.createInstance(
                    instanceInfo = {
                        flags = if (VK_KHR_PORTABILITY_ENUMERATION_EXTENSION_NAME in ENABLED_INSTANCE_EXTENSIONS) {
                            VK_INSTANCE_CREATE_ENUMERATE_PORTABILITY_BIT_KHR
                        } else {
                            0u
                        }
                        ppEnabledExtensionNames = ENABLED_INSTANCE_EXTENSIONS.toCStringArray(memScope)
                        enabledExtensionCount = ENABLED_INSTANCE_EXTENSIONS.size.toUInt()
                    }
                ).use { block(it) }
            }
        }

    companion object {
        @OptIn(ExperimentalNativeApi::class)
        private val ENABLED_INSTANCE_EXTENSIONS = buildList {
            add(VK_EXT_DEBUG_UTILS_EXTENSION_NAME)
            if (Platform.osFamily == OsFamily.MACOSX || Platform.osFamily == OsFamily.IOS) {
                add(VK_KHR_PORTABILITY_ENUMERATION_EXTENSION_NAME)
            }
        }
    }
}
