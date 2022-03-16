package solve.engine

import model.board.Board

data class SolvePassResult(val newBoard: Board, val step: SolveStep.Change) {
}
