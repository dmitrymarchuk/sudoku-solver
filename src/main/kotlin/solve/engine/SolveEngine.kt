package solve.engine

import SolvePassFactory
import model.board.Board
import model.board.HouseType
import mu.KotlinLogging
import solve.engine.executors.MultiStepExecutor
import solve.engine.executors.PassExecutor
import solve.pass.HiddenSingle
import solve.pass.MarkPossible
import solve.pass.NakedSingle
import solve.pass.NakedSubSet
import util.product

private val logger = KotlinLogging.logger {}

class SolveEngine(private val initialBoard: Board) {
  fun getSolveSequence(): Sequence<SolveStep> = sequence {
    yield(SolveStep.Initial(initialBoard))
    yield(
      MultiStepExecutor(
        markPossible,
        nakedSingle,
        hiddenSingle,
        nakedSubSets
      )(initialBoard))
  }

  companion object {
    val nakedSubSets: PassExecutor
      get() {
        val sizes = listOf(2, 3, 4)
        val houses = HouseType.values().toList()
        val product = sizes.product(houses)
        return MultiStepExecutor(
          *product
            .map { (size, houseType) -> NakedSubSet.factory(houseType, size) }
            .map(SolvePassFactory::toExecutor)
            .toTypedArray()
        )
          .exhausting()
      }

    val hiddenSingle: PassExecutor
      get() = MultiStepExecutor(
        HiddenSingle.Companion::rows then nakedSingle,
        HiddenSingle.Companion::columns then nakedSingle,
        HiddenSingle.Companion::blocks then nakedSingle)
        .exhausting()

    val nakedSingle
      get() =
        (::NakedSingle combined markPossible)
          .exhausting()

    val markPossible: PassExecutor
      get() =
        ::MarkPossible.checkSolved()
  }
}

