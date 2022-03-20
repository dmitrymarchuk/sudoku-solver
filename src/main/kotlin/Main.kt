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
import model.board.HouseType
import model.cell.Cell
import mu.KotlinLogging
import parse.loadSudokus
import solve.engine.SolveEngine
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
  val initialBoard = loadSudokus().first()
  val solveSequence = SolveEngine(initialBoard).getSolveSequence().iterator()

  Window(onCloseRequest = ::exitApplication) {
    var step by remember { mutableStateOf(solveSequence.next()) }

    fun checkSolved() =
      step.board.isSolved.also {
        if (it) logger.info { "Board solved!" }
      }

    fun checkSequenceEmpty() =
      (!solveSequence.hasNext()).also {
        if (it) logger.warn { "Could not solve the board." }
      }

    Box(
      modifier = Modifier
        .widthIn(min = 400.dp, max = 800.dp)
        .clickable {
          if (checkSolved() || checkSequenceEmpty()) return@clickable

          step = solveSequence.next()
          logger.info {
            "Performed solving step $step"
          }

          if (checkSolved()) return@clickable
        }
    ) {
      App(step.board.houses(HouseType.Block))
    }
  }
}
