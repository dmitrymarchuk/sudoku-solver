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

fun loadEasy() = loadLinesSequence("easy.csv")
  .map { it.split(",") }
  .map { (first, second) -> Pair(first, second) }
  .map { it.map(Board::fromString) }

fun loadHard() = loadLinesSequence("hard.txt")
  .map(Board::fromString)

fun loadSudokus() =
//  loadEasy().map { it.first }
  loadHard()

