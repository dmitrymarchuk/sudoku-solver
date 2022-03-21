package model.board

import model.cell.Cell

class House(cells: List<Cell>) : List<Cell> by cells {

  class HouseBuilder private constructor(private val cells: MutableList<Cell>) : List<Cell> by cells {
    fun set(index: Int, cell: Cell): HouseBuilder {
      cells[index] = cell;
      return this
    }

    fun house(): House = House(cells)
  }
}

