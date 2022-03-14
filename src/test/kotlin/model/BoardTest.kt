package model

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class BoardTest {
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
}
