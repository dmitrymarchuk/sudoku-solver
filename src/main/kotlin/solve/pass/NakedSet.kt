package solve.pass

import model.board.Board
import model.board.HouseType
import solve.SolvePassBase
import solve.engine.SolveStep

class NakedSet(
  private val type: HouseType,
  private val amount: Int,
  initialBoard: Board,
) : SolvePassBase(initialBoard) {
  override fun executeInternal(): SolveStep.Change {
    TODO("Not yet implemented")
  }
}
