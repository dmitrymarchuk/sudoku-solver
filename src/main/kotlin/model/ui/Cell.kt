package model.ui

sealed class Cell {
  object Empty : Cell()
  class Value(val value: Int) : Cell() {
    companion object {
      val Int.cell
        get() = Value(this)
    }
  }

  class Multi(
    val subCells: List<SubCell>,
  ) : Cell(),
      List<SubCell> by subCells {
    init {
      assert(subCells.size == 9)
    }
  }
}
