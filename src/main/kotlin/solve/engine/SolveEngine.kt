package solve.engine

import solve.SolvePassFactory
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
    val mainExecutor = MultiStepExecutor(
      markPossible,
      nakedSingle,
      hiddenSingle,
      nakedSubSets
    )
    yield(mainExecutor(initialBoard))
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
            .map { it then hiddenSingle }
            .toTypedArray()
        )
          .exhausting()
      }

    val hiddenSingle: PassExecutor
      get() = MultiStepExecutor(
        *HouseType
          .values()
          .map { HiddenSingle.factory(it) then nakedSingle }
          .toTypedArray())
        .exhausting()

    val nakedSingle
      get() =
        (NakedSingle.factory combined markPossible)
          .exhausting()

    val markPossible: PassExecutor
      get() = MarkPossible.factory.checkSolved()
  }
}

