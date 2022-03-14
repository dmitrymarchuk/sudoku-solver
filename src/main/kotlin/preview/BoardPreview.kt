package preview

import model.ui.BoardModel
import model.ui.Cell
import model.ui.Cell.Value.Companion.cell
import model.ui.SubCell
import model.ui.SubCell.Companion.crossedOut
import model.ui.SubCell.Companion.highlighted
import model.ui.SubCell.Companion.subCell

val sampleBoard: BoardModel = BoardModel.from(
  listOf(1.cell, 2.cell, 3.cell, 4.cell, 5.cell, 6.cell, 7.cell, 8.cell, 9.cell),
  listOf(1.cell, 2.cell, 3.cell, 4.cell, 5.cell, 6.cell, 7.cell, 8.cell, 9.cell),
  listOf(1.cell, 2.cell, 3.cell, 4.cell, 5.cell, 6.cell, 7.cell, 8.cell, 9.cell),
  listOf(1.cell, 2.cell, 3.cell, 4.cell, 5.cell, 6.cell, 7.cell, 8.cell, 9.cell),
  listOf(
    Cell.Multi(listOf(
      1.subCell,
      2.crossedOut,
      3.subCell,
      SubCell.Empty,
      5.highlighted,
      6.subCell,
      7.crossedOut,
      8.subCell,
      SubCell.Empty,
    )),
    2.cell,
    3.cell,
    4.cell,
    5.cell,
    6.cell,
    7.cell,
    8.cell,
    9.cell),
  listOf(1.cell, 2.cell, 3.cell, 4.cell, 5.cell, 6.cell, 7.cell, 8.cell, 9.cell),
  listOf(1.cell, 2.cell, 3.cell, 4.cell, 5.cell, 6.cell, 7.cell, 8.cell, 9.cell),
  listOf(1.cell, 2.cell, 3.cell, 4.cell, 5.cell, 6.cell, 7.cell, 8.cell, 9.cell),
  listOf(1.cell, 2.cell, 3.cell, 4.cell, 5.cell, 6.cell, 7.cell, 8.cell, 9.cell),
)
