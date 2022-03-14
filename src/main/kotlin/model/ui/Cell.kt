package model.ui

sealed class Cell {
  object Empty : Cell()
  sealed class Value() : Cell() {
    abstract val value: Int

    data class Single(override val value: Int) : Value() {
      init {
        assert(value in 1..9)
      }

      companion object {
        val Int.cell
          get() = Single(this)
      }
    }

    data class Multi(
      override val value: Int,
      val subCells: List<SubCell>,
    ) : Value(),
        List<SubCell> by subCells {
      init {
        assert(value in 1..9)
        assert(subCells.size == 9)
      }
    }
  }

}
