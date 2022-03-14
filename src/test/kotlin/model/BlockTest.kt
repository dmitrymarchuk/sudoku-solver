package model

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import util.repeat
import java.lang.AssertionError

internal class BlockTest {
  @Test
  fun testFromList() {
    var block = Block.fromList(9.repeat(0))
    assertEquals(0u, block.value)

    block = Block.fromList(9.repeat(1))
    assertEquals(2u, block.value)

    block = Block.fromList((1..9).toList())
    assertEquals(0x3feu, block.value)

    assertThrows<AssertionError> {
      block = Block.fromList(10.repeat(0))
    }

    assertThrows<AssertionError> {
      block = Block.fromList(emptyList())
    }

    assertThrows<AssertionError> {
      block = Block.fromList(listOf(1))
    }
  }

  @Test
  fun hasTest() {
    var block = Block.fromList(9.repeat(0))
    assertThrows<AssertionError> {
      block.has(0)
    }
    (1..9).forEach {
      assertFalse(block.has(it))
    }
    assertTrue(block.hasNoNumbers)
    assertFalse(block.hasAnyNumber)

    block = Block.fromList((1..9).toList())
    (1..9).forEach {
      assertTrue(block.has(it))
    }
    println(block.value)
    assertTrue(block.hasAllNumbers)

    block = Block.fromList((1..9).toList().map { if (it != 5) it else 0 })
    (1..9).forEach {
      if (it == 5)
        assertFalse(block.has(it))
      else
        assertTrue(block.has(it))
    }
    assertFalse(block.hasAllNumbers)
    assertFalse(block.hasNoNumbers)
  }
}
