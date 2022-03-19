package parse

import model.board.Board
import util.map
import java.io.BufferedReader
import java.io.InputStreamReader

fun loadLinesSequence(file: String) = Thread
  .currentThread()
  .contextClassLoader
  .getResource(file)!!
  .openStream()
  .let { BufferedReader(InputStreamReader(it)) }
  .lineSequence()

fun loadSource1() = loadLinesSequence("sudokus.csv")
  .map { it.split(",") }
  .map { (first, second) -> Pair(first, second) }
  .map { it.map(Board::fromString) }

fun loadSudokus() =
  loadSource1().map { it.first }

