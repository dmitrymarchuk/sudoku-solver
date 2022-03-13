package ui.board

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import board.UIThreeByThreeGrid
import model.Cell
import model.MultiCell
import model.SubCell

@Composable
fun BoxScope.UICell(cell: Cell, maxWidth: Dp) {
  when (cell) {
    is Cell.Empty -> {
      //do nothing
    }
    is Cell.Value -> {
      val fontSize = with(LocalDensity.current) { (maxWidth / 3).toSp() }
      Text(
        fontSize = fontSize,
        text = cell.value.toString(),
        modifier = Modifier.align(Alignment.Center)
      )
    }
    is MultiCell  -> {
      UIThreeByThreeGrid(maxWidth) { position ->
        val fontSize = with(LocalDensity.current) { (maxWidth / 3 / 1.25f).toSp() }
        val subCell = cell.subCells[position]
        Text(
          fontSize = fontSize,
          text = when (subCell) {
            SubCell.Empty          ->
              ""
            is SubCell.Normal      ->
              subCell.value.toString()
            is SubCell.Highlighted ->
              subCell.value.toString()
            is SubCell.CrossedOut  ->
              subCell.value.toString()
          },
          modifier = Modifier.align(Alignment.Center)
        )
      }
    }
  }
}
