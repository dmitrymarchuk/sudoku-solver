package solve.engine

import model.board.Board
import util.indexesDiff

sealed class SolveStep {
  abstract val board: Board
  abstract val noChanges: Boolean

  data class Initial(override val board: Board) : SolveStep() {
    override val noChanges = true
  }

  sealed class Change : SolveStep() {
    abstract val oldBoard: Board

    data class Cells(
      override val board: Board,
      override val oldBoard: Board,
      val changedIndices: List<Int>,
    ) : Change() {
      constructor(board: Board, oldBoard: Board) : this(board,
        oldBoard,
        oldBoard.indexesDiff(board))

      override val noChanges = changedIndices.isEmpty()
    }

    data class MultiStep(
      private val subSteps: List<Change>,
    ) : Change(), List<Change> by subSteps {
      override val oldBoard: Board = subSteps.first().oldBoard
      override val board: Board = subSteps.last().board
      override val noChanges = subSteps.all { it.noChanges }
    }
  }
}
