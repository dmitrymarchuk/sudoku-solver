package model

sealed class Cell {
  object Empty : Cell()
  class Value(val value: Int) : Cell() {
    companion object {
      fun Int.toCell(): Value = Value(this)
    }
  }
}
