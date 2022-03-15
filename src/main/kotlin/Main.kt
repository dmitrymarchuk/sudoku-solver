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
import model.cell.Cell
import mu.KotlinLogging
import parse.loadCsv
import solve.engine.SolveContext
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

  Window(onCloseRequest = ::exitApplication) {
    var context by remember { mutableStateOf(SolveContext.initial(loadCsv().first())) }

    Box(
      modifier = Modifier
        .widthIn(min = 400.dp, max = 800.dp)
        .clickable {
          if (context.isSolved) return@clickable

          context = context.solveAndNextContext()
          logger.warn {
            "Performed solving step ${context.lastStep}"
          }
          if (context.isSolved) {
            logger.warn {
              "Board solved!"
            }
          }
        }
    ) {
      App(context.board.houses)
    }
  }
}
