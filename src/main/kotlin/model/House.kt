package model

class House(val cells: List<Cell>) : SimpleGrid<Cell>(cells) {
  companion object {
    fun fromCells(vararg cells: Cell) =
      House(cells.toList())

    fun fromNumbers(vararg cells: Int) =
      House(cells.map { Cell.Value(it) }.toList())
  }
}
