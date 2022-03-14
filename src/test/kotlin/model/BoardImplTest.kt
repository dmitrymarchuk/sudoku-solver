package model

import model.interfaces.Board
import model.ui.Cell
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import util.repeat

internal class BoardImplTest {

  @Test
  fun isSolvedTest() {
    val board = Board.fromString(("864371259325849761971265843436192587198657432" +
        "257483916689734125713528694542916378"))
    assertTrue(board.isSolved)
  }

  @Test
  fun isNotSolvedTest() {
    val board = Board.fromString(("004300209005009001070060043006002087190007" +
        "400050083000600000105003508690042910300"))
    assertFalse(board.isSolved)
  }

  @Test
  fun isNotSolvedTest2() {
    val board = Board.fromString("68" + "4371259325849761971265843436192587198657432" +
        "257483916689734125713528694542916378")
    assertFalse(board.isSolved)
  }

  @Test
  fun getVisitorArgsHouse() {
    val board = Board.fromString(81.repeat("0").joinToString("")) as BoardImpl
    val cell = Cell.Empty

    assertEquals(0, board.getVisitorArgsForIndex(10, cell).house)
    assertEquals(1, board.getVisitorArgsForIndex(3, cell).house)
    assertEquals(2, board.getVisitorArgsForIndex(26, cell).house)

    assertEquals(3, board.getVisitorArgsForIndex(47, cell).house)
    assertEquals(4, board.getVisitorArgsForIndex(48, cell).house)
    assertEquals(5, board.getVisitorArgsForIndex(42, cell).house)

    assertEquals(6, board.getVisitorArgsForIndex(74, cell).house)
    assertEquals(7, board.getVisitorArgsForIndex(68, cell).house)
    assertEquals(8, board.getVisitorArgsForIndex(71, cell).house)
  }

  @Test
  fun getVisitorArgsRow() {
    val board = Board.fromString(81.repeat("0").joinToString("")) as BoardImpl
    val cell = Cell.Empty

    assertEquals(0, board.getVisitorArgsForIndex(8, cell).row)
    assertEquals(1, board.getVisitorArgsForIndex(17, cell).row)
    assertEquals(2, board.getVisitorArgsForIndex(18, cell).row)
    assertEquals(3, board.getVisitorArgsForIndex(31, cell).row)
    assertEquals(4, board.getVisitorArgsForIndex(39, cell).row)
    assertEquals(5, board.getVisitorArgsForIndex(51, cell).row)
    assertEquals(6, board.getVisitorArgsForIndex(54, cell).row)
    assertEquals(7, board.getVisitorArgsForIndex(68, cell).row)
    assertEquals(8, board.getVisitorArgsForIndex(78, cell).row)
  }

  @Test
  fun getVisitorArgsCol() {
    val board = Board.fromString(81.repeat("0").joinToString("")) as BoardImpl
    val cell = Cell.Empty

    assertEquals(0, board.getVisitorArgsForIndex(0, cell).col)
    assertEquals(1, board.getVisitorArgsForIndex(73, cell).col)
    assertEquals(2, board.getVisitorArgsForIndex(56, cell).col)
    assertEquals(3, board.getVisitorArgsForIndex(3, cell).col)
    assertEquals(4, board.getVisitorArgsForIndex(13, cell).col)
    assertEquals(5, board.getVisitorArgsForIndex(23, cell).col)
    assertEquals(6, board.getVisitorArgsForIndex(33, cell).col)
    assertEquals(7, board.getVisitorArgsForIndex(70, cell).col)
    assertEquals(8, board.getVisitorArgsForIndex(71, cell).col)
  }
}
