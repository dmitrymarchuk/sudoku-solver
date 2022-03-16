package model.impl

import model.board.NumbersSet
import model.board.NumbersSetImpl
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import util.repeat
import util.replace

internal class NumbersSetImplTest {
  private fun get(l: List<Int>) = NumbersSet.fromNumbers(l) as NumbersSetImpl

  @Test
  fun fromNumbersTest() {
    var set = get(emptyList())
    assertEquals(0u, set.value)

    set = get(listOf(1))
    assertEquals(2u, set.value)

    assertThrows<AssertionError> {
      set = get(9.repeat(1))
    }

    assertThrows<AssertionError> {
      set = get(listOf(1, 2, 3, 4, 5, 1))
    }

    set = get(9.repeat(0).replace(0, 1))
    assertEquals(2u, set.value)

    set = get((1..9).toList())
    assertEquals(0x3feu, set.value)
  }

  @Test
  fun hasTest() {
    var block = get(9.repeat(0))
    assertThrows<AssertionError> {
      block.has(0)
    }
    (1..9).forEach {
      assertFalse(block.has(it))
    }
    assertTrue(block.hasNoNumbers)
    assertFalse(block.hasAnyNumber)

    block = get((1..9).toList())
    (1..9).forEach {
      assertTrue(block.has(it))
    }
    println(block.value)
    assertTrue(block.hasAllNumbers)

    block = get((1..9).toList().map { if (it != 5) it else 0 })
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
