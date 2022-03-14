package model

import model.interfaces.NumbersSet
import util.assertOneToNine
import util.check
import util.oneToNine

private val nineOnes = 0x3FEu

data class NumbersSetImpl(val value: UInt) : NumbersSet {
  override val hasAllNumbers get() = value xor nineOnes == 0u
  override val hasNoNumbers get() = value == 0u
  override val hasAnyNumber by lazy { value.countOneBits() > 0 }

  override val missingNumbers by lazy { oneToNine.filterNot(value::check).toList() }
  override val presentNumbers by lazy { oneToNine.filter(value::check).toList() }

  override fun has(number: Int): Boolean {
    number.assertOneToNine()
    return value.check(number)
  }
}
