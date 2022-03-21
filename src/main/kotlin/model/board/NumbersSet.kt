package model.board

import model.cell.Cell
import util.assertZeroToNine
import util.check
import util.set

interface NumbersSet {
  val hasAllNumbers: Boolean
  val hasNoNumbers: Boolean
  val hasAnyNumber: Boolean

  val missingNumbers: List<Int>
  val presentNumbers: List<Int>

  fun has(number: Int): Boolean

  companion object {
    fun fromHouse(house: House) = fromNumbers(house.map {
      when (it) {
        is Cell.Single -> it.value
        else           -> 0
      }
    })

    fun fromNumbers(numbers: List<Int>): NumbersSet {
      var result = 0u

      numbers.forEach {
        it.assertZeroToNine()
        if (it > 0) {
          assert(!result.check(it))
          result = result.set(it)
        }
      }

      return NumbersSetImpl(result)
    }
  }
}
