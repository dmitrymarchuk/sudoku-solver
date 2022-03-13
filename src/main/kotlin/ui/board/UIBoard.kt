package ui.board

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import board.UiGrid
import model.Board
import model.House

@Composable
@Preview
fun UiBoard(
  board: Board,
) {
  BoxWithConstraints(modifier = Modifier.widthIn(200.dp, 500.dp)) {
    val box = this
    UiGrid(
      maxWidth,
      modifier = Modifier.border(
        2.dp, Color.Black
      ),
      innerBorderWidth = 0.5.dp,
      innerBorderColor = Color.Gray
    ) { position ->
      UiHouse(board[position] as House, box.maxWidth / 3)
    }
  }
}

