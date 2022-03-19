package solve.pass

import model.board.HouseType
import model.cell.Cell
import model.cell.ValueCell
import parse.loadSource1
import util.oneToNine
import kotlin.test.Test
import kotlin.test.assertTrue

internal class HiddenSingleTest {

  @Test
  fun test() {
    val type = HouseType.Row

    val markedBoard =
      MarkPossible(
        loadSource1().first().first
      ).execute().board

    val board = HiddenSingle(type, markedBoard).execute().board

    board.visitCells { args ->
      val cell = args.cell
      if (cell !is Cell.Multi) return@visitCells
      val houseWithoutCurrent =
        args.house(type).filter { it !== cell }.filterIsInstance<Cell.Multi>()
      oneToNine
        .map(cell::getSubCell)
        .filterIsInstance<ValueCell>()
        .forEach { candidate ->
          assertTrue(
            houseWithoutCurrent.none { it.getSubCell(candidate.value) is ValueCell })
        }
    }
  }
}
