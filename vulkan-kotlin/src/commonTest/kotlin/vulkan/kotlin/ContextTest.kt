package vulkan.kotlin

import kotlinx.cinterop.MemScope
import kotlinx.cinterop.memScoped
import volk.VK_VERSION_MAJOR
import volk.VK_VERSION_MINOR
import kotlin.experimental.ExperimentalNativeApi
import kotlin.test.Test
import kotlin.test.assertTrue

@OptIn(ExperimentalNativeApi::class)
class ContextTest {
    @Test
    fun `create instance`() = withContext {
        val instance = it.createInstance(instanceInfo = { withExtensions() })
        instance.close()
    }

    @Test
    fun `get instance version`() = withContext {
        val instanceVersion = it.instanceVersion

        assertTrue(VK_VERSION_MAJOR(instanceVersion) >= 1u)
        assertTrue(VK_VERSION_MINOR(instanceVersion) >= 3u)
    }

    @Test
    fun `enumerate instance extensions`() = withContext {
        val extensions = it.enumerateInstanceExtensionProperties().toList()

        assertTrue(extensions.isNotEmpty())
    }

    @Test
    fun `enumerate instance layers`() = withContext {
        val extensions = it.enumerateInstanceLayerProperties().toList()

        assertTrue(extensions.isNotEmpty())
    }

    private fun withContext(block: MemScope.(Context) -> Unit) =
        memScoped { Context().use { block(it) } }
}
