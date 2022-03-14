package model.ui

sealed class SubCell {
  object Empty : SubCell()

  data class Possible(val value: Int) : SubCell()
  data class CrossedOut(val value: Int) : SubCell()
  data class Highlighted(val value: Int) : SubCell()
  companion object {
    val Int.possible
      get() = Possible(this)
    val Int.crossedOut
      get() = CrossedOut(this)
    val Int.highlighted
      get() = Highlighted(this)
  }
}
