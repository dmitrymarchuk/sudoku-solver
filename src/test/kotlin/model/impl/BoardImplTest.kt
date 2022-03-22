package model.impl

import model.board.Board
import model.board.HouseType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Timeout
import java.util.concurrent.TimeUnit

@Timeout(2, unit = TimeUnit.SECONDS)
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
    val board = Board.fromString("80" + "4371259325849761971265843436192587198657432" +
        "257483916689734125713528694542916378")
    assertFalse(board.isSolved)
  }

  @Test
  fun getVisitorArgsHouse() {
    assertEquals(0, Board.getHouseIndexForCellIndex(HouseType.Block, 10))
    assertEquals(1, Board.getHouseIndexForCellIndex(HouseType.Block, 3))
    assertEquals(2, Board.getHouseIndexForCellIndex(HouseType.Block, 26))

    assertEquals(3, Board.getHouseIndexForCellIndex(HouseType.Block, 47))
    assertEquals(4, Board.getHouseIndexForCellIndex(HouseType.Block, 48))
    assertEquals(5, Board.getHouseIndexForCellIndex(HouseType.Block, 42))

    assertEquals(6, Board.getHouseIndexForCellIndex(HouseType.Block, 74))
    assertEquals(7, Board.getHouseIndexForCellIndex(HouseType.Block, 68))
    assertEquals(8, Board.getHouseIndexForCellIndex(HouseType.Block, 71))
  }

  @Test
  fun getVisitorArgsRow() {
    assertEquals(0, Board.getHouseIndexForCellIndex(HouseType.Row, 8))
    assertEquals(1, Board.getHouseIndexForCellIndex(HouseType.Row, 17))
    assertEquals(2, Board.getHouseIndexForCellIndex(HouseType.Row, 18))
    assertEquals(3, Board.getHouseIndexForCellIndex(HouseType.Row, 31))
    assertEquals(4, Board.getHouseIndexForCellIndex(HouseType.Row, 39))
    assertEquals(5, Board.getHouseIndexForCellIndex(HouseType.Row, 51))
    assertEquals(6, Board.getHouseIndexForCellIndex(HouseType.Row, 54))
    assertEquals(7, Board.getHouseIndexForCellIndex(HouseType.Row, 68))
    assertEquals(8, Board.getHouseIndexForCellIndex(HouseType.Row, 78))
  }

  @Test
  fun getVisitorArgsCol() {
    assertEquals(0, Board.getHouseIndexForCellIndex(HouseType.Column, 0))
    assertEquals(1, Board.getHouseIndexForCellIndex(HouseType.Column, 73))
    assertEquals(2, Board.getHouseIndexForCellIndex(HouseType.Column, 56))
    assertEquals(3, Board.getHouseIndexForCellIndex(HouseType.Column, 3))
    assertEquals(4, Board.getHouseIndexForCellIndex(HouseType.Column, 13))
    assertEquals(5, Board.getHouseIndexForCellIndex(HouseType.Column, 23))
    assertEquals(6, Board.getHouseIndexForCellIndex(HouseType.Column, 33))
    assertEquals(7, Board.getHouseIndexForCellIndex(HouseType.Column, 70))
    assertEquals(8, Board.getHouseIndexForCellIndex(HouseType.Column, 71))
  }
}
