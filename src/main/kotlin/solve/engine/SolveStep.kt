package solve.engine

import model.cell.Cell

sealed class SolveStep {
  val noChanges get() = this is NoChanges

  object Initial : SolveStep()
  object NoChanges : SolveStep()
  data class SingleCellChanged(val index: Int, val oldCell: Cell, val newCell: Cell) : SolveStep()
}
