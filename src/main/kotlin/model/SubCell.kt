package model

sealed class SubCell {
  object Empty : SubCell()

  class Normal(val value: Int) : SubCell() {
    companion object {
      fun Int.toSubCell(): Normal = Normal(this)
    }
  }
  class CrossedOut(val value: Int) : SubCell()
  class Highlighted(val value: Int) : SubCell()
}
