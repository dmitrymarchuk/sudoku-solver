package solve.pass

import model.board.Board
import model.cell.Cell
import org.junit.jupiter.api.Test
import kotlin.test.assertTrue
import kotlin.test.fail

internal class NakedSingleTest {
  @Test
  fun test() {
    val board = Board.fromString("004300209005009001070060043006002087190007400050083000600000105003508690042910300")
    val markedBoard = MarkPossible(board).execute()?.board ?: fail()
    val step = NakedSingle(markedBoard).execute() ?: fail()
    assertTrue(step.changedIndices.isNotEmpty())

    step.board.visitCells { (cell) ->
      when (cell) {
        Cell.Empty     -> Unit
        is Cell.Multi  -> assertTrue(cell.size > 1)
        is Cell.Single -> Unit
      }
    }
  }
}
