package model

sealed class SubCell {
  object Empty : SubCell()

  class Normal(val value: Int) : SubCell()
  class CrossedOut(val value: Int) : SubCell()
  class Highlighted(val value: Int) : SubCell()
  companion object {
    val Int.subCell
      get() = Normal(this)
    val Int.crossedOut
      get() = CrossedOut(this)
    val Int.highlighted
      get() = Highlighted(this)
  }
}
