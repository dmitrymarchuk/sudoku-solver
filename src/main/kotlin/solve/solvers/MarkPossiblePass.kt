package solve.solvers

import model.board.Board
import model.board.BoardVisitor
import model.cell.Cell
import mu.KotlinLogging
import solve.EachCellSolvePass

private val logger = KotlinLogging.logger {}

class MarkPossiblePass : EachCellSolvePass() {
  override fun prepare(board: Board) {
    super.prepare(board)
    logger.info { "Calculating all possible cell values" }
  }

  override fun cellVisitor(board: Board, args: BoardVisitor.Args): Board {
    val (cell, index, row, col, house) = args

    return when (cell) {
      Cell.Empty, is Cell.Multi -> {
        val possible = listOf(row, col, house)
          .map { it.missingNumbers.toSet() }
          .reduce { acc, set -> acc.intersect(set) }
        val newCell = (if (cell is Cell.Empty) {
          Cell.Multi.Empty
        } else {
          cell as Cell.Multi
        }).setPossible(possible.toList())

        logger.debug { "Calculated possible cell values for cell $index: $newCell" }

        board.setCell(index, newCell)
      }
      is Cell.Single            -> board
    }
  }
}
