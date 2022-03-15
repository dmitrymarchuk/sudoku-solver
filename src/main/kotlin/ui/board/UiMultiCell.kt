package ui.board

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import board.UiGrid
import model.ui.Cell
import model.ui.SubCell
import java.awt.SystemColor.text

@Composable
fun UiMultiCell(cell: Cell.Multi, maxWidth: Dp) {
  UiGrid(maxWidth) { position ->
    val fontSize = with(LocalDensity.current) { (maxWidth / 3 / 1.25f).toSp() }
    val subCell = cell[position + 1]
    val modifier = Modifier.align(Alignment.Center)
    when (subCell) {
      SubCell.Empty         ->
        Unit
      is SubCell.Possible   ->
        Text(
          modifier = modifier,
          fontSize = fontSize,
          color = Color.LightGray,
          text = subCell.value.toString()
        )
      is SubCell.CrossedOut ->
        Text(
          modifier = modifier,
          fontSize = fontSize,
          text = subCell.value.toString(),
          color = Color.Red,
          style = TextStyle(textDecoration = TextDecoration.LineThrough)
        )
      is SubCell.Highlighted ->
        Text(
          modifier = modifier,
          fontSize = fontSize,
          text = subCell.value.toString(),
          color = Color.Blue
        )
    }
  }
}
