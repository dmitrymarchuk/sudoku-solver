package model.impl

import model.board.Board
import model.board.BoardImpl
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import parse.loadSource1
import solve.engine.SolveEngine
import util.repeat

internal class BoardImplTest {
  @Test
  fun sudokuSourceTest() {
    loadSource1().forEach { (initial, solved) ->
      assertEquals(solved, SolveEngine(initial).getSolveSequence().last().board)
    }
  }

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
    val board = Board.fromString(81.repeat("0").joinToString("")) as BoardImpl

    assertEquals(0, board.getVisitorBlocksIndexes(10).third)
    assertEquals(1, board.getVisitorBlocksIndexes(3).third)
    assertEquals(2, board.getVisitorBlocksIndexes(26).third)

    assertEquals(3, board.getVisitorBlocksIndexes(47).third)
    assertEquals(4, board.getVisitorBlocksIndexes(48).third)
    assertEquals(5, board.getVisitorBlocksIndexes(42).third)

    assertEquals(6, board.getVisitorBlocksIndexes(74).third)
    assertEquals(7, board.getVisitorBlocksIndexes(68).third)
    assertEquals(8, board.getVisitorBlocksIndexes(71).third)
  }

  @Test
  fun getVisitorArgsRow() {
    val board = Board.fromString(81.repeat("0").joinToString("")) as BoardImpl

    assertEquals(0, board.getVisitorBlocksIndexes(8).first)
    assertEquals(1, board.getVisitorBlocksIndexes(17).first)
    assertEquals(2, board.getVisitorBlocksIndexes(18).first)
    assertEquals(3, board.getVisitorBlocksIndexes(31).first)
    assertEquals(4, board.getVisitorBlocksIndexes(39).first)
    assertEquals(5, board.getVisitorBlocksIndexes(51).first)
    assertEquals(6, board.getVisitorBlocksIndexes(54).first)
    assertEquals(7, board.getVisitorBlocksIndexes(68).first)
    assertEquals(8, board.getVisitorBlocksIndexes(78).first)
  }

  @Test
  fun getVisitorArgsCol() {
    val board = Board.fromString(81.repeat("0").joinToString("")) as BoardImpl

    assertEquals(0, board.getVisitorBlocksIndexes(0).second)
    assertEquals(1, board.getVisitorBlocksIndexes(73).second)
    assertEquals(2, board.getVisitorBlocksIndexes(56).second)
    assertEquals(3, board.getVisitorBlocksIndexes(3).second)
    assertEquals(4, board.getVisitorBlocksIndexes(13).second)
    assertEquals(5, board.getVisitorBlocksIndexes(23).second)
    assertEquals(6, board.getVisitorBlocksIndexes(33).second)
    assertEquals(7, board.getVisitorBlocksIndexes(70).second)
    assertEquals(8, board.getVisitorBlocksIndexes(71).second)
  }
}
