package ui.board

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import board.UiGrid
import model.House

@Composable
@Preview
fun UiHouse(house: House, maxWidth: Dp, modifier: Modifier = Modifier) {
  UiGrid(
    maxWidth,
    modifier,
    Color.LightGray,
    0.2.dp,
  ) { position ->
    UiCell(house[position], maxWidth / 3)
  }
}
