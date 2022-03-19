package util

fun <T> List<T>.groupBy9(): List<List<T>> {
  this.size.assertNineSq()

  return zeroUntilNine.map { this.drop(9 * it).take(9) }
}

fun <T, R> Pair<T, T>.map(mapper: (T) -> R): Pair<R, R> {
  return Pair(mapper(first), mapper(second))
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

fun <T> List<T>.replace(index: Int, value: T): List<T> =
  this.toMutableList().apply { set(index, value) }.toList()

fun <T> List<T>.quadrant(n: Int): List<T> {
  this.size.assertNineSq()

  assert(n in zeroUntilNine)

  val firstIndex =
    if (n < 3) n * 3
    else if (n < 6) (9 * 3) + ((n % 3) * 3)
    else (9 * 6) + ((n % 3) * 3)

  val firstRow = this.drop(firstIndex).take(3)
  val secondRow = this.drop(firstIndex + 9).take(3)
  val thirdRow = this.drop(firstIndex + 18).take(3)

  return firstRow + secondRow + thirdRow
}
