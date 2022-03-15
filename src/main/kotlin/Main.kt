import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import model.ui.Cell
import mu.KotlinLogging
import parse.loadCsv
import solve.BoardSolver
import ui.board.UiBoard

@Composable
fun App(board: List<List<Cell>>) {
  MaterialTheme {
    UiBoard(board)
  }
}

private val logger = KotlinLogging.logger {}
fun main() = application {
  logger.info { "Assertions enabled: ${javaClass.desiredAssertionStatus()}" }
  val board = BoardSolver.markPossible(loadCsv().first())
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
