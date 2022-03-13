package model

sealed class Cell {
  object Empty : Cell()
  class Value(val value: Int) : Cell() {
    companion object {
      val Int.cell
        get() = Value(this)
    }
  }
}
