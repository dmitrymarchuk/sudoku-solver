package model.cell

import util.assertNine
import util.assertOneToNine
import util.repeat
import util.replace

sealed class Cell {

  companion object {
    fun fromChar(char: Char) =
      when {
        char == '0' || char == '.' -> Empty
        char.isDigit()             -> Single(char.digitToInt())
        else                       ->
          throw IllegalArgumentException()
      }
  }

  object Empty : Cell() {
    override fun toString(): String {
      return "${Cell::class.simpleName}.${Empty::class.simpleName}"
    }
  }

  data class Single(override val value: Int) : Cell(), ValueCell {
    init {
      value.assertOneToNine()
    }

    companion object {
      val Int.cell
        get() = Single(this)
    }
  }

  data class Multi(
    private val subCells: List<SubCell>,
  ) : Cell(), Collection<SubCell> by subCells {

    init {
      subCells.size.assertNine()
    }

    val possible = subCells
      .filterIsInstance<ValueCell>()
      .filter { it !is SubCell.CrossedOut }

    fun getSubCell(number: Int): SubCell {
      number.assertOneToNine()
      return subCells[number - 1]
    }

    fun setSubCell(number: Int, cell: SubCell): Multi {
      number.assertOneToNine()
      return Multi(subCells.replace(number - 1, cell))
    }

    fun setPossible(possible: List<Int>): Multi {
      assert(possible.size <= 9)

      val result: MutableList<SubCell> = 9.repeat(SubCell.Empty).toMutableList()
      possible.map {
        it.assertOneToNine()

        result[it - 1] = when (this.getSubCell(it)) {
          SubCell.Empty          -> SubCell.Possible(it)
          is SubCell.CrossedOut  -> SubCell.CrossedOut(it)
          is SubCell.Highlighted -> SubCell.Highlighted(it)
          is SubCell.Possible    -> SubCell.Possible(it)
        }
      }
      return Multi(result)
    }

    companion object {
      val Empty = Multi(9.repeat(SubCell.Empty))
    }
  }
}
