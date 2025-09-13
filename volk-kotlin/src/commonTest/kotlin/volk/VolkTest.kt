package volk

import kotlin.test.Test
import kotlin.test.assertEquals

class VolkTest {
    @Test
    fun `Volk initialization`() {
        val result = volkInitialize()

        assertEquals(VK_SUCCESS, result)
    }
}
