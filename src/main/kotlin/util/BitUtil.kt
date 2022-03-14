package util

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
