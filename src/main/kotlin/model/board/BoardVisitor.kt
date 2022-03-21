package model.board

import model.cell.Cell

interface BoardVisitor {
  fun visitCells(visitor: (Args) -> Unit)

  interface Args {
    val cell: Cell
    val index: Int
    fun set(type: HouseType): NumbersSet
    fun house(type: HouseType): House

    val houses get() = HouseType.values().map(this::house)
    val numberSets get() = HouseType.values().map(this::set)
  }
}
