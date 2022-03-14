package ui.board

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import model.Cell

@Composable
fun BoxScope.UiCell(cell: Cell, maxWidth: Dp) {
  when (cell) {
    is Cell.Empty -> {
      //do nothing
    }
    is Cell.Value -> {
      Text(
        fontSize = with(LocalDensity.current) { (maxWidth / 2).toSp() },
        fontWeight = FontWeight.Light,
        text = cell.value.toString(),
        modifier = Modifier.align(Alignment.Center)
      )
    }
    is Cell.Multi -> {
      UiMultiCell(cell, maxWidth)
    }
  }
}
