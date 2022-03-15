package solve.impl

import model.interfaces.Board
import model.ui.Cell
import model.ui.SubCell
import org.junit.jupiter.api.Test
import solve.BoardSolver
import kotlin.test.assertFalse
import kotlin.test.assertEquals
import kotlin.test.fail

internal class BoardSolverTest {
  @Test
  fun markPossibleTest() {
    val board = Board.fromString("004300209005009001070060043006002087190007400050083000600000105003508690042910300")
    val markedBoard = BoardSolver.markPossible(board)

    assertEquals(0, markedBoard.filterIsInstance<Cell.Empty>().size)
    markedBoard.visitCells { dummyBoard, (multiCell, _, row, col, house) ->
      if (multiCell is Cell.Multi) {
        multiCell.forEach { subCell ->
          when (subCell) {
            is SubCell.Empty       -> Unit
            is SubCell.CrossedOut  -> fail()
            is SubCell.Highlighted -> fail()
            is SubCell.Possible    -> {
              assertFalse(row.has(subCell.value))
              assertFalse(col.has(subCell.value))
              assertFalse(house.has(subCell.value))
            }
          }
        }
      }
      dummyBoard
    }
  }
}
