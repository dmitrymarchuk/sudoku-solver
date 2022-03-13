package ui.board

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import board.UIThreeByThreeGrid
import model.House

@Composable
@Preview
fun UIHouse(house: House, maxWidth: Dp, modifier: Modifier = Modifier) {
  UIThreeByThreeGrid(
    maxWidth,
    modifier,
    Color.LightGray,
    0.2.dp,
  ) { position ->
    UICell(house[position], maxWidth / 3)
  }
}
