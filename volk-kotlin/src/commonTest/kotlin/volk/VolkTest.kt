package volk

import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class VolkTest {
    @AfterTest
    fun tearDown() {
        volkFinalize()
    }

    @Test
    fun `simple test`() {
        assertEquals(2, 1+1)
    }

    @Test
    fun `Volk initialization`() {
        val result = volkInitialize()

        assertEquals(VK_SUCCESS, result)
    }

    @Test
    fun `instance version`() {
        val instanceVersion = volkGetInstanceVersion()

        assertNotEquals(0u, instanceVersion)
    }
}
