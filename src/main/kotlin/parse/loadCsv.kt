package parse

import model.board.Board
import java.io.BufferedReader
import java.io.InputStreamReader

fun loadCsv() = Thread
  .currentThread()
  .contextClassLoader
  .getResource("sudokus.csv")!!
  .openStream()
  .let {
    BufferedReader(InputStreamReader(it))
  }
  .lineSequence()
  .map { it.split(",").first() }
  .map(Board::fromString)
