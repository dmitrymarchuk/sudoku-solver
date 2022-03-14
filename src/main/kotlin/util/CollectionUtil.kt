package util

fun <T> List<T>.groupBy9(): List<List<T>> {
  assert(this.size == 9 * 9)

  return (0 until 9).map { this.drop(9 * it).take(9) }
}

fun <T> Int.repeat(value: T): List<T> {
  return (0 until this).map { value }
}

fun <T> List<List<T>>.transpose(): List<List<T>> {
  return (0 until this[0].size).map { x ->
    (this.indices).map { y ->
      this[y][x]
    }
  }
}

fun <T> List<List<T>>.rotate(): List<List<T>> {
  return this.transpose().map { it.reversed() }
}

fun <T> List<T>.quadrant(n: Int): List<T> {
  assert(this.size == 9 * 9)

  assert(n in 0 until 9)

  val firstIndex =
    if (n < 3) n * 3
    else if (n < 6) (9 * 3) + ((n % 3) * 3)
    else (9 * 6) + ((n % 3) * 3)

  val firstRow = this.drop(firstIndex).take(3)
  val secondRow = this.drop(firstIndex + 9).take(3)
  val thirdRow = this.drop(firstIndex + 18).take(3)

  return firstRow + secondRow + thirdRow
}
