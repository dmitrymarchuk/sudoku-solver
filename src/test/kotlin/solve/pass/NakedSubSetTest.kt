package solve.pass

import model.board.Board
import model.board.HouseType
import model.cell.Cell
import model.cell.SubCell
import org.junit.jupiter.api.Test
import solve.engine.SolveStep
import util.repeat
import util.replace
import kotlin.test.assertEquals
import kotlin.test.assertFalse

internal class NakedSubSetTest {
  @Test
  fun nakedPairsTest() {
    val board = MarkPossible(Board.fromString(
      "700849030" +
          "928135006" +
          "400267089" +
          "642783951" +
          "397451628" +
          "815692300" +
          "204516093" +
          "100008060" +
          "500004010"
    )).execute().board
    val withoutNakedStep = NakedSubSet(
      HouseType.Row,
      2,
      board
    ).execute() as SolveStep.Change.Cells
    assertFalse(withoutNakedStep.noChanges)
    assertEquals(listOf(64, 73, 78), withoutNakedStep.changedIndices)
    assertEquals(
      listOf(
        Cell.Multi(9
          .repeat(SubCell.Empty)
          .replace(2, SubCell.CrossedOut(3))
          .replace(6, SubCell.Possible(7))
        ),
        Cell.Multi(9
          .repeat(SubCell.Empty)
          .replace(2, SubCell.Possible(3))
          .replace(5, SubCell.Possible(6))
          .replace(6, SubCell.CrossedOut(7))
          .replace(7, SubCell.Possible(8))
        ),
        Cell.Multi(9
          .repeat(SubCell.Empty)
          .replace(1, SubCell.CrossedOut(2))
          .replace(6, SubCell.CrossedOut(7))
          .replace(7, SubCell.Possible(8))
        ),
      ), withoutNakedStep.changedIndices.map { withoutNakedStep.board[it] })
  }

  @Test
  fun nakedTripleTest() {
    val board = MarkPossible(Board.fromString(
      "000294380" +
          "000178640" +
          "480356100" +
          "004837501" +
          "000415700" +
          "500629834" +
          "953782416" +
          "126543978" +
          "040961253"
    )).execute().board

    val withoutNakedStep = NakedSubSet(
      HouseType.Column,
      3,
      board
    ).execute() as SolveStep.Change.Cells
    assertFalse(withoutNakedStep.noChanges)
    assertEquals(listOf(1), withoutNakedStep.changedIndices)
    assertEquals(
      listOf(
        Cell.Multi(9
          .repeat(SubCell.Empty)
          .replace(0, SubCell.Possible(1))
          .replace(5, SubCell.CrossedOut(6))
          .replace(6, SubCell.Possible(7))
        ),
      ), withoutNakedStep.changedIndices.map { withoutNakedStep.board[it] })
  }
}
