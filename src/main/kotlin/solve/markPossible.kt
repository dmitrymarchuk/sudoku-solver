package solve

import model.interfaces.Board
import model.ui.Cell

fun markPossible(initial: Board): Board {
  return initial.visitCells { board, (cell, index, row, col, house) ->
    when (cell) {
      Cell.Empty, is Cell.Multi -> {
        val possible = listOf(row, col, house).map { it.missingNumbers }.flatten().toSet()
        val newCell = (if (cell is Cell.Empty) {
          Cell.Multi.Empty
        } else {
          cell as Cell.Multi
        }).setPossible(possible.toList())

        board.setCell(index, newCell)
      }
      is Cell.Single            -> board
    }
  }
}
