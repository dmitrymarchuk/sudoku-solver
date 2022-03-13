package model

import model.Grid3x3.Companion.assertSize

class Board private constructor(val houses: List<House>) : Grid3x3.Composite<Cell>,
                                                           List<Grid3x3<Cell>> by houses {
  init {
    assertSize(houses)
  }

  companion object {
    fun fromHouses(vararg houses: House) =
      Board(houses.toList())
  }
}

