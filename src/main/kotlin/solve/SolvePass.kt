package solve

import model.board.Board

interface SolvePass {
  fun transform(board: Board): Board
}
