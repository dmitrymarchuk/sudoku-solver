package model.parse

import model.Board
import model.ui.BoardModel
import model.ui.Cell
import java.io.BufferedReader
import java.io.InputStreamReader

fun loadCsv(): Sequence<Board> {
  return Thread
    .currentThread()
    .contextClassLoader
    .getResource("sudokus.csv")!!
    .openStream()
    .let {
      BufferedReader(InputStreamReader(it))
    }
    .lineSequence()
    .map { it.split(",").drop(1).first() }
    .map(Board::fromString)
}
