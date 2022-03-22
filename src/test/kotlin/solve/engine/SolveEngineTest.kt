package solve.engine

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import parse.loadEasy
import parse.loadHard

internal class SolveEngineTest {
  @Test
  fun allEasyTest() {
    loadEasy().forEach { (initial, solved) ->
      val vasa = SolveEngine(initial).getSolveSequence().last().board
      val result = solved == vasa
      assertTrue(result)
    }
  }

  @Test
  fun hardTest() {
    loadHard().take(1).forEach {
      assertTrue(SolveEngine(it).getSolveSequence().last().board.isSolved)
    }
  }
}
