package preview

import model.Board
import model.Cell.Value.Companion.toCell
import model.House
import model.MultiCell
import model.SimpleGrid
import model.SubCell.Normal.Companion.toSubCell

val sampleBoard: Board = Board.fromHouses(
  House.fromNumbers(1, 2, 3, 4, 5, 6, 7, 8, 9),
  House.fromNumbers(1, 2, 3, 4, 5, 6, 7, 8, 9),
  House.fromNumbers(1, 2, 3, 4, 5, 6, 7, 8, 9),
  House.fromNumbers(1, 2, 3, 4, 5, 6, 7, 8, 9),
  House.fromCells(
    MultiCell(SimpleGrid.from(
      1.toSubCell(),
      2.toSubCell(),
      3.toSubCell(),
      4.toSubCell(),
      5.toSubCell(),
      6.toSubCell(),
      7.toSubCell(),
      8.toSubCell(),
      9.toSubCell(),
    )),
    2.toCell(),
    3.toCell(),
    4.toCell(),
    5.toCell(),
    6.toCell(),
    7.toCell(),
    8.toCell(),
    9.toCell()),
  House.fromNumbers(1, 2, 3, 4, 5, 6, 7, 8, 9),
  House.fromNumbers(1, 2, 3, 4, 5, 6, 7, 8, 9),
  House.fromNumbers(1, 2, 3, 4, 5, 6, 7, 8, 9),
  House.fromNumbers(1, 2, 3, 4, 5, 6, 7, 8, 9),
)
