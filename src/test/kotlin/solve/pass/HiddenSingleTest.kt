package solve.pass

import model.board.HouseType
import model.cell.Cell
import model.cell.ValueCell
import parse.loadSource1
import solve.engine.SolveStep
import util.oneToNine
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class HiddenSingleTest {

  @Test
  fun testRow() {
    testHouse(HouseType.Row)
  }

  @Test
  fun testCol() {
    testHouse(HouseType.Row)
  }

  @Test
  fun testBlock() {
    testHouse(HouseType.Row)
  }

  private fun testHouse(type: HouseType) {
    val markedBoard =
      MarkPossible(
        loadSource1().first().first
      ).execute().board

    val step = HiddenSingle(type, markedBoard).execute() as SolveStep.Change.Cells
    assertFalse(step.noChanges)

    val changedIndicesClone = step.changedIndices.toMutableList()
    step.oldBoard.visitCells { args ->
      val cell = args.cell
      if (cell !is Cell.Multi) return@visitCells
      val house =
        args.house(type).filterIsInstance<Cell.Multi>()
      oneToNine
        .map(cell::getSubCell)
        .filterIsInstance<ValueCell>()
        .forEach { candidate ->
          val size = house
            .map { it.getSubCell(candidate.value) }
            .filterIsInstance<ValueCell>()
            .size
          if (size == 1) {
            assertContains(changedIndicesClone, args.index)
            changedIndicesClone.remove(args.index)
          }
        }
    }
    assertTrue(changedIndicesClone.isEmpty())
  }
}
