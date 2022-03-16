package solve.pass

import model.board.Board
import model.cell.Cell
import model.cell.SubCell
import org.junit.jupiter.api.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import kotlin.test.fail

internal class MarkPossibleTest {
  @Test
  fun test() {
    val board = Board.fromString("00430020900500900107006004300600208719" +
        "0007400050083000600000105003508690042910300")
    val pass = MarkPossible(board)
    val step = pass.execute() ?: fail()

    assertTrue(step.changedIndices.isNotEmpty())
    assertTrue(step.changedIndices.map(step.board::get).filterIsInstance<Cell.Empty>().isEmpty())

    step.board.visitCells { (multiCell, _, row, col, house) ->
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
    }
  }
}
