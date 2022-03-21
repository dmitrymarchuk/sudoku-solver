package ui.board

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import board.UiGrid
import model.cell.Cell

@Composable
fun UiBoard(board: List<List<Cell>>) {
  BoxWithConstraints(modifier = Modifier.widthIn(200.dp, 500.dp)) {
    val maxWidthOrHeight = min(maxWidth, maxHeight)
    UiGrid(
      maxWidthOrHeight,
      modifier = Modifier.border(
        2.dp, Color.Black
      ),
      innerBorderWidth = 0.5.dp,
      innerBorderColor = Color.Gray
    ) { position ->
      UiHouse(board[position], maxWidthOrHeight / 3)
    }
  }
}

