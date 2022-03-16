package solve.engine

import model.board.Board

sealed class SolveStep {
  abstract val board: Board

  class Initial(override val board: Board) : SolveStep()
  class NoChange(override val board: Board) : SolveStep()

  sealed class Change : SolveStep() {
    abstract val oldBoard: Board

    data class ValueCellsChanged(
      override val board: Board,
      override val oldBoard: Board,
      val changedIndices: List<Int>,
    ) : Change()

    data class NonValueCellsChanged(
      override val board: Board,
      override val oldBoard: Board,
      val changedIndices: List<Int>,
    ) : Change()

    class MultiStep(
      val subSteps: List<Change>,
    ) : Change() {
      override val oldBoard: Board = subSteps.first().oldBoard
      override val board: Board = subSteps.last().board
    }
  }
}
