package model.board

import mu.KotlinLogging
import util.assertOneToNine
import util.check
import util.oneToNine

private val nineOnes = 0x3FEu
private val logger = KotlinLogging.logger {}

class NumbersSetImpl(val value: UInt) : NumbersSet {
  override val hasAllNumbers get() = value xor nineOnes == 0u
  override val hasNoNumbers get() = value == 0u
  override val hasAnyNumber by lazy { value.countOneBits() > 0 }

  override val missingNumbers by lazy { oneToNine.filterNot(value::check).toList() }
  override val presentNumbers by lazy { oneToNine.filter(value::check).toList() }

  override fun has(number: Int): Boolean {
    number.assertOneToNine()
    return value.check(number)
  }

  override fun toString(): String {
    return "${this::class.simpleName}(value=${value}, " +
        "${this::hasAllNumbers.name}=${hasAllNumbers}, " +
        "${this::hasNoNumbers.name}=${hasNoNumbers}), " +
        "${this::hasAnyNumber.name}=${hasAnyNumber}, " +
        "${this::missingNumbers.name}=${this.missingNumbers}, " +
        "${this::presentNumbers.name}=${this.presentNumbers}, "
  }
}
