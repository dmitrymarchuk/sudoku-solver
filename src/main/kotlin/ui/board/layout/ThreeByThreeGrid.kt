package board

import BorderDirection
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import border
import java.util.*

@Composable
fun UIThreeByThreeGrid(
  maxWidth: Dp,
  modifier: Modifier = Modifier,
  innerBorderColor: Color = Color.Transparent,
  innerBorderWidth: Dp = 0.dp,
  content: @Composable BoxScope.(position: Int) -> Unit,
) {
  Column(modifier = modifier) {
    for (row in 0 until 3) {
      Row {
        for (col in 0 until 3) {
          val position = (row * 3) + col
          Box(
            modifier = Modifier
              .width(maxWidth / 3)
              .height(maxWidth / 3)
              .border(
                innerBorderWidth,
                innerBorderColor,
                sidesByPosition(position)
              )
          ) {
            content(position)
          }
        }
      }
    }
  }
}

private fun sidesByPosition(position: Int): EnumSet<BorderDirection> {
  return when (position) {
    0    -> EnumSet.of(BorderDirection.Bottom, BorderDirection.Right)
    1    -> EnumSet.of(BorderDirection.Bottom, BorderDirection.Right, BorderDirection.Left)
    2    -> EnumSet.of(BorderDirection.Bottom, BorderDirection.Left)
    3    -> EnumSet.of(BorderDirection.Top, BorderDirection.Right, BorderDirection.Bottom)
    4    -> EnumSet.of(
      BorderDirection.Bottom,
      BorderDirection.Right,
      BorderDirection.Left,
      BorderDirection.Top
    )
    5    -> EnumSet.of(BorderDirection.Bottom, BorderDirection.Left, BorderDirection.Top)
    6    -> EnumSet.of(BorderDirection.Top, BorderDirection.Right)
    7    -> EnumSet.of(BorderDirection.Left, BorderDirection.Right, BorderDirection.Top)
    8    -> EnumSet.of(BorderDirection.Top, BorderDirection.Left)
    else -> throw IllegalArgumentException(
      "Wrong position $position. Must be 0 <= p <= 9"
    )
  }
}
