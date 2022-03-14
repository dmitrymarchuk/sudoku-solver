package util

fun <T> List<T>.groupBy9(): List<List<T>> {
  assert(this.size == 9 * 9)

  return (0 until 9).map { this.take(9) }
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

fun UInt.set(bit: Int): UInt {
  return this or (1u shl bit)
}

fun UInt.clear(bit: Int): UInt {
  return this and (1u shl bit).inv()
}

fun UInt.flip(bit: Int): UInt {
  return this xor (1u shl bit)
}

fun UInt.check(bit: Int): Boolean {
  return (this shr bit) and 1u == 1u
}

fun UInt.set(bit: Int, value: Boolean): UInt {
  return (this and (1u shl bit).inv()) or ((if (value) 1u else 0u) shl bit)
}
