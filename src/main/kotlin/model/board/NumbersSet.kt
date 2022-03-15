package model.board

import model.cell.Cell
import util.assertNine
import util.assertZeroToNine
import util.set

interface NumbersSet {
  val hasAllNumbers: Boolean
  val hasNoNumbers: Boolean
  val hasAnyNumber: Boolean

  val missingNumbers: List<Int>
  val presentNumbers: List<Int>

  fun has(number: Int): Boolean

  companion object {
    fun fromCells(cells: List<Cell>) = fromNumbers(cells.map {
      when (it) {
        is Cell.Single -> it.value
        else           -> 0
      }
    })

    fun fromNumbers(numbers: List<Int>): NumbersSet {
      numbers.size.assertNine()
      var result = 0u

      numbers.forEach {
        it.assertZeroToNine()
        if (it > 0) result = result.set(it)
      }

      return NumbersSetImpl(result)
    }
  }
}
