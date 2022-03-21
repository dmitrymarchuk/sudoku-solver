package solve.pass

import model.cell.Cell
import model.cell.SubCell
import parse.loadEasy
import solve.engine.SolveStep
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import kotlin.test.fail

internal class MarkPossibleTest {
  @org.junit.jupiter.api.Test
  fun test() {
    val board = loadEasy().first().first
    val pass = MarkPossible(board)
    val step = pass.execute().takeUnless { it.noChanges } ?: fail()

    step as SolveStep.Change.Cells

    assertTrue(step.changedIndices.isNotEmpty())
    assertTrue(step.changedIndices
      .map(step.board::get)
      .filterIsInstance<Cell.Empty>()
      .isEmpty())

    step.board.visitCells { args ->
      val multiCell = args.cell
      if (multiCell is Cell.Multi) {
        multiCell.forEach { subCell ->
          when (subCell) {
            is SubCell.Empty       -> Unit
            is SubCell.CrossedOut  -> fail()
            is SubCell.Highlighted -> fail()
            is SubCell.Possible    ->
              args.numberSets.forEach { assertFalse(it.has(subCell.value)) }
          }
        }
      }
    }
  }
}
