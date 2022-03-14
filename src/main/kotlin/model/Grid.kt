package model

open class Grid<T> protected constructor(val elements: List<T>) : List<T> by elements {
  init {
    assert(elements.size == 9)
  }

  companion object {
    fun <T> from(vararg elements: T) =
      Grid(elements.toList())
  }
}

