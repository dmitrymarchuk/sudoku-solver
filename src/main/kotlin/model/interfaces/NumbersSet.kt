package model.interfaces

import model.NumbersSetImpl
import model.ui.Cell
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
      assert(numbers.size == 9)
      var result = 0u

      numbers.forEach {
        assert(it in 0..9)
        if (it > 0) result = result.set(it)
      }

      return NumbersSetImpl(result)
    }
  }
}
