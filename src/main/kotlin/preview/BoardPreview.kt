package preview

import model.Board
import model.Cell
import model.Cell.Value.Companion.cell
import model.Grid
import model.SubCell
import model.SubCell.Companion.crossedOut
import model.SubCell.Companion.highlighted
import model.SubCell.Companion.subCell

val sampleBoard: Board = Board.from(
  Grid.from(1.cell, 2.cell, 3.cell, 4.cell, 5.cell, 6.cell, 7.cell, 8.cell, 9.cell),
  Grid.from(1.cell, 2.cell, 3.cell, 4.cell, 5.cell, 6.cell, 7.cell, 8.cell, 9.cell),
  Grid.from(1.cell, 2.cell, 3.cell, 4.cell, 5.cell, 6.cell, 7.cell, 8.cell, 9.cell),
  Grid.from(1.cell, 2.cell, 3.cell, 4.cell, 5.cell, 6.cell, 7.cell, 8.cell, 9.cell),
  Grid.from(
    Cell.Multi(Grid.from(
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
  Grid.from(1.cell, 2.cell, 3.cell, 4.cell, 5.cell, 6.cell, 7.cell, 8.cell, 9.cell),
  Grid.from(1.cell, 2.cell, 3.cell, 4.cell, 5.cell, 6.cell, 7.cell, 8.cell, 9.cell),
  Grid.from(1.cell, 2.cell, 3.cell, 4.cell, 5.cell, 6.cell, 7.cell, 8.cell, 9.cell),
  Grid.from(1.cell, 2.cell, 3.cell, 4.cell, 5.cell, 6.cell, 7.cell, 8.cell, 9.cell),
)
