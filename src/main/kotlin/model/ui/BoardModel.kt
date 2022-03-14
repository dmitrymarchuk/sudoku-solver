package model.ui

import util.groupBy9

class BoardModel(val cells: List<List<Cell>>) : List<List<Cell>> by cells {
  init {
    assert(cells.size == 9)
  }

  companion object {
    fun from(vararg elements: Cell): BoardModel =
      BoardModel(elements.toList().groupBy9())

    fun from(vararg houses: List<Cell>): BoardModel =
      BoardModel(houses.toList())
  }
}
