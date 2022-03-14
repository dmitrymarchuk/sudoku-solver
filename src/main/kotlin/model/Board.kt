package model

class Board(val cells: List<List<Cell>>) : List<List<Cell>> by cells {
  init {
    assert(cells.size == 9)
  }

  companion object {
    fun from(vararg elements: Cell): Board =
      Board((0 until 9).map { elements.take(9) })

    fun from(vararg houses: List<Cell>): Board =
      Board(houses.toList())
  }
}
