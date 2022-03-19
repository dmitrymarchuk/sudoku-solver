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

fun <T> List<List<T>>.rotateCCW(): List<List<T>> {
  return this.map { it.reversed() }.transpose()
}

fun <T> List<List<T>>.rotateCW(): List<List<T>> {
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

fun <T> List<List<T>>.rowsToQuadrants(): List<List<T>> {
  val flat = this.flatten()
  return zeroUntilNine.map(flat::quadrant)
}

fun <T> List<List<T>>.quadrantsToRows(): List<List<T>> {
  return zeroUntilNine.map { row ->
    val q = (row / 3) * 3
    val i = (row % 3) * 3

    this[q + 0].subList(i, i + 3) +
        this[q + 1].subList(i, i + 3) +
        this[q + 2].subList(i, i + 3)
  }
}

fun <T> Iterable<T>.toIndexed(): List<Indexed<T>> {
  return this.mapIndexed { i, v -> Indexed(i, v) }
}

fun <T> List<T>.indexesDiff(other: List<T>): List<Int> {
  assert(this.size == other.size)

  return this
    .zip(other)
    .toIndexed()
    .filter { it.value.let { (first, second) -> first != second } }
    .map { it.index }
}

data class Indexed<T>(val index: Int, val value: T)
