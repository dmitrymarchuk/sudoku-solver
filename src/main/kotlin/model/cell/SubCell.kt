package model.cell

sealed class SubCell {
  object Empty : SubCell() {
    override fun toString(): String {
      return "${SubCell::class.simpleName}.${Empty::class.simpleName}"
    }
  }

  data class Possible(override val value: Int) : SubCell(), ValueCell
  data class CrossedOut(override val value: Int) : SubCell(), ValueCell
  data class Highlighted(override val value: Int) : SubCell(), ValueCell
  companion object {
    val Int.possible
      get() = Possible(this)
    val Int.crossedOut
      get() = CrossedOut(this)
    val Int.highlighted
      get() = Highlighted(this)
  }
}
