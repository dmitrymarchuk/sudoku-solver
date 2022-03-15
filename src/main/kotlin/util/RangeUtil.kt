package util

val oneToNine = 1..9
val zeroUntilNine = 0 until 9
val zeroToNine = 0..9

const val nineSq = 9 * 9

fun Int.assertOneToNine() {
  assert(this in oneToNine)
}

fun Int.assertZeroUntilNine() {
  assert(this in zeroUntilNine)
}

fun Int.assertZeroToNine() {
  assert(this in zeroToNine)
}

fun Int.assertNineSq() {
  assert(this == nineSq)
}

fun Int.assertNine() {
  assert(this == 9)
}
