package model.ui

import util.assertOneToNine
import util.repeat

sealed class Cell {
  object Empty : Cell()
  data class Single(val value: Int) : Cell() {
    init {
      value.assertOneToNine()
    }

    companion object {
      val Int.cell
        get() = Single(this)
    }
  }

  data class Multi(
    val subCells: List<SubCell>,
  ) : Cell(),
      List<SubCell> by subCells {
    init {
      assert(subCells.size == 9)
    }

    fun setPossible(possible: List<Int>): Multi {
      return this
    }
    companion object {
      val Empty = Multi(9.repeat(SubCell.Empty))
    }
  }
}
