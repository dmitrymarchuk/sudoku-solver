package model.board

import model.cell.Cell

interface BoardVisitor {
  fun visitCells(visitor: (Args) -> Unit)

  data class Args(
    val cell: Cell,
    val index: Int,
    val row: NumbersSet,
    val col: NumbersSet,
    val house: NumbersSet,
  )
}
