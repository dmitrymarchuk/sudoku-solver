package util

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class BitUtilTest {
  @Test
  fun set() {
    assertEquals(0xC0u, 0x0u.set(6).set(7))
    assertEquals(0x1u, 0x0u.set(0))
    assertEquals(0x80000001u, 0x0u.set(31).set(0))
  }

  @Test
  fun clear() {
    assertEquals(0x0u, 0x80000001u.clear(0).clear(31))
  }

  @Test
  fun flip() {
    val value = 0x34234u
    assertEquals(value,
      value
        .flip(5)
        .flip(5)
        .flip(10)
        .flip(10)
        .flip(31)
        .flip(31)
        .flip(0)
        .flip(0))
  }

  @Test
  fun check() {
    assertTrue(0x1u.check(0))
    assertTrue(0x8201u.check(15))
    assertFalse(0x0u.check(31))
  }

  @Test
  fun testSet() {
    assertEquals(0x8001u, 0x0u.set(0, true).set(15, true))
  }

}
