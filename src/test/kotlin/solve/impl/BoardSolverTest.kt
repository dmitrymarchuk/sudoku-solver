package solve.impl

import model.board.Board
import model.cell.Cell
import model.cell.SubCell
import org.junit.jupiter.api.Test
import solve.solvers.MarkPossiblePass
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.fail

internal class MarkPossiblePassTest {
  @Test
  fun test() {
    val pass = MarkPossiblePass()
    val board = Board.fromString("004300209005009001070060043006002087190007400050083000600000105003508690042910300")
    val markedBoard = pass.transform(board)

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
