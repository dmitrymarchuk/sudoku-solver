package model

import model.ui.Cell
import util.check
import util.set

val nineOnes = 0x3FEu

@JvmInline
value class Block(val value: UInt) {
  val hasAllNumbers get() = value xor nineOnes == 0u
  val hasNoNumbers get() = value == 0u
  val hasAnyNumber get() = value.countOneBits() > 0

  fun has(number: Int): Boolean {
    assert(number in 1..9)
    return value.check(number)
  }

  companion object {
    fun fromCells(cells: List<Cell>) =
      fromNumbers(cells.map {
        when (it) {
          is Cell.Value -> it.value
          else          -> 0
        }
      })

    fun fromNumbers(numbers: List<Int>): Block {
      assert(numbers.size == 9)
      var result = 0u

      numbers.forEach {
        assert(it in 0..9)
        if (it > 0)
          result = result.set(it)
      }

      return Block(result)
    }
  }
}
