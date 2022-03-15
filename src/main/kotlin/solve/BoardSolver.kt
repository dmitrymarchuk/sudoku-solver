package solve

import model.interfaces.Board
import model.ui.Cell
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

object BoardSolver {
  fun markPossible(initial: Board): Board {
    logger.info { "Calculating possible cell values" }
    return initial.visitCells { board, (cell, index, row, col, house) ->
      when (cell) {
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
}
