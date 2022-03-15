package solve.engine

import model.board.Board

class SolveContext private constructor(
  val board: Board,
  private val state: SolveState,
  val lastStep: SolveStep,
) {

  val isSolved get() = board.isSolved

  fun solveAndNextContext(): SolveContext {
    return this
  }

  companion object {
    fun initial(board: Board) = SolveContext(
      board,
      SolveState(false),
      SolveStep.Initial,
    )
  }
}

data class SolveState(val possibleMarked: Boolean)
