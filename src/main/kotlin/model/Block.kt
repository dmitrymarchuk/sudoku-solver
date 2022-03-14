package model

import util.set

val tenOnes = 0x3FFu
val tenOnesInv = 0x3FFu.toInt().inv().toUInt()

@JvmInline
value class Block(val value: UInt) {
  fun hasAllNumbers() = value xor 0xffffffu == 0u

  companion object {
    fun fromList(numbers: List<Int>): Block {
      assert(numbers.size == 9)
      var result = 0u

      numbers.forEachIndexed { i, number ->
        assert(number in 0..9)
        if (i > 0)
          result = result.set(number)
      }

      return Block(result)
    }
  }
}
