package model

open class SimpleGrid<T> protected constructor(
  val elements: List<T>,
) : Grid3x3.Simple<T>,
    List<T> by elements {
  init {
    Grid3x3.assertSize(elements)
  }

  companion object {
    fun <T> from(vararg elements: T) =
      SimpleGrid(elements.toList())
  }
}
