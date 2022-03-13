package model

sealed interface Grid3x3<T> {
  interface Simple<T> : Grid3x3<T>, List<T>
  interface Composite<T> : Grid3x3<T>, List<Grid3x3<T>>

  companion object {
    fun <T> assertSize(list: List<T>) {
      assert(list.size == 9)
    }
  }
}
