package solve.engine

import model.board.Board
import model.board.HouseType
import solve.engine.executors.ExhaustingExecutor.Companion.exhausting
import solve.engine.executors.MultiStepExecutor
import solve.engine.executors.MultiStepExecutor.Companion.combined
import solve.engine.executors.MultiStepExecutor.Companion.then
import solve.engine.executors.PassExecutor
import solve.engine.executors.SinglePassExecutor
import solve.engine.executors.SinglePassExecutor.Companion.toExecutor
import solve.pass.HiddenSingle
import solve.pass.MarkPossible
import solve.pass.NakedSingle
import solve.pass.NakedSubSet
import util.product

class SolveEngine(private val initialBoard: Board) {
  fun getSolveSequence(): Sequence<SolveStep> = sequence {
    yield(SolveStep.Initial(initialBoard))
    val mainExecutor = MultiStepExecutor
      .multiple(
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
        return MultiStepExecutor
          .multiple(
            *product
              .map { (size, houseType) -> NakedSubSet.factory(houseType, size) }
              .map(::SinglePassExecutor)
              .map { it combined markPossible combined hiddenSingle }
              .toTypedArray()
          )
          .exhausting()
      }

    val hiddenSingle: PassExecutor
      get() = MultiStepExecutor.multiple(
        *HouseType
          .values()
          .map { HiddenSingle.factory(it) combined markPossible combined nakedSingle }
          .toTypedArray())
        .exhausting()

    val nakedSingle
      get() =
        (NakedSingle.factory combined markPossible)
          .exhausting()

    val markPossible: PassExecutor
      get() = MarkPossible.factory.toExecutor()
  }
}

