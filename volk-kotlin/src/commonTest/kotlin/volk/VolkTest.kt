package volk

import kotlin.test.Test
import kotlin.test.assertEquals

class VolkTest {
    @Test
    fun `Volk initialization`() {
        val result = volkInitialize()

        assertEquals(VK_SUCCESS, result)
    }

    @Test
    fun `instance version`() {
        val instanceVersion = volkGetInstanceVersion()

        println("Instance version: $instanceVersion")
        println("${VK_VERSION_MAJOR(instanceVersion)}.${VK_VERSION_MINOR(instanceVersion)}.${VK_VERSION_PATCH(instanceVersion)}")
    }
}
