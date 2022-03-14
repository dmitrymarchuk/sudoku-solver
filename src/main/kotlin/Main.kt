import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import parse.loadCsv
import model.ui.Cell
import solve.markPossible
import ui.board.UiBoard

@Composable
fun App(board: List<List<Cell>>) {
  MaterialTheme {
    UiBoard(board)
  }
}

fun main() = application {
  val board = markPossible(loadCsv().first())
  Window(onCloseRequest = ::exitApplication) {
//    var board by remember { mutableStateOf(list) }
    Box(
      modifier = Modifier
        .widthIn(min = 400.dp, max = 800.dp)
//        .clickable { board = markPossible(board) }
    ) {
      App(board.houses)
    }
  }
}
