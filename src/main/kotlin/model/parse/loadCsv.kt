package model.parse

import model.ui.BoardModel
import model.ui.Cell
import java.io.BufferedReader
import java.io.InputStreamReader

fun loadCsv(): Sequence<BoardModel> {
  return Thread
    .currentThread()
    .contextClassLoader
    .getResource("sudokus.csv")!!
    .openStream()
    .let {
      BufferedReader(InputStreamReader(it))
    }
    .lineSequence()
    .map { it.split(",").first() }
    .map {
      val cells = it.map {
        if (it == '0') Cell.Empty
        else if (it.isDigit()) Cell.Value(it.digitToInt())
        else throw IllegalArgumentException("Illegal char $it")
      }
      BoardModel.from(*cells.toTypedArray())
    }
}
