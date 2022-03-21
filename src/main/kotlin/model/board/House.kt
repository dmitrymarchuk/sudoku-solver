package model.board

import model.cell.Cell
import util.filterIsInstanceIndexed
import util.toIndexed

class House(
  private val type: HouseType,
  private val cells: List<Cell>,
) : List<Cell> by cells {
  val numbersSet: NumbersSet = NumbersSet.fromHouse(this)

  val multiCells by lazy { cells.filterIsInstance<Cell.Multi>() }
  val multiCellsIndexed by lazy {
    cells
      .toIndexed()
      .filterIsInstanceIndexed<Cell.Multi>()
  }

  val builder
    get() =
      HouseBuilder(cells.toMutableList())

  fun set(index: Int, cell: Cell): HouseBuilder {
    return builder.also {
      this.set(index, cell)
    }
  }

  inner class HouseBuilder(
    private val cells: MutableList<Cell>,
  ) {
    operator fun set(index: Int, element: Cell): Cell {
      cells[index] = element
      return element
    }

    fun build(): House = House(type, cells)
  }
}

