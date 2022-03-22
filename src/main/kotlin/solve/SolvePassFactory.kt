package solve

import model.board.Board
import solve.SolvePass

data class SolvePassFactory(val name: String, val create: (Board) -> SolvePass) {
  operator fun invoke(board: Board): SolvePass = create(board)
}
