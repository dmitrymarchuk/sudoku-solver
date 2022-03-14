package util

val oneToNine = 1..9
val zeroUntilNine = 0 until 9
const val nineSq = 9 * 9

fun Int.assertOneToNine() {
  assert(this in oneToNine)
}

fun Int.assertNineSq() {
  assert(this == nineSq)
}
