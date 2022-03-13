import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.Dp
import java.util.*

/**
 * Border definition can be extended to provide border style or [androidx.compose.ui.graphics.Brush]
 * One more way is make it sealed class and provide different implementations:
 * SolidBorder, DashedBorder etc
 */
data class BorderLine(val strokeWidth: Dp, val color: Color)

enum class BorderDirection {
  Top,
  Right,
  Bottom,
  Left
}

@Stable
fun Modifier.border(
  width: Dp,
  color: Color,
  directions: EnumSet<BorderDirection>,
) = border(
  top = if (directions.contains(BorderDirection.Top)) BorderLine(width, color) else null,
  right = if (directions.contains(BorderDirection.Right)) BorderLine(width, color) else null,
  bottom = if (directions.contains(BorderDirection.Bottom)) BorderLine(width, color) else null,
  left = if (directions.contains(BorderDirection.Left)) BorderLine(width, color) else null,
)

@Stable
fun Modifier.border(
  top: BorderLine? = null,
  right: BorderLine? = null,
  bottom: BorderLine? = null,
  left: BorderLine? = null,
) =
  drawBehind {
    top?.let {
      drawTop(it, shareStart = left != null, shareEnd = right != null)
    }
    right?.let {
      drawRight(it, shareTop = top != null, shareBottom = bottom != null)
    }
    bottom?.let {
      drawBottom(border = it, shareStart = left != null, shareEnd = right != null)
    }
    left?.let {
      drawLeft(it, shareTop = top != null, shareBottom = bottom != null)
    }
  }

private fun DrawScope.drawTop(
  border: BorderLine,
  shareStart: Boolean = true,
  shareEnd: Boolean = true,
) {
  val stroke = border.strokeWidth.toPx()
  if (stroke == 0f) return
  drawPath(
    Path().apply {
      moveTo(0f, 0f)
      lineTo(if (shareStart) stroke else 0f, stroke)
      val width = size.width
      lineTo(if (shareEnd) width - stroke else width, stroke)
      lineTo(width, 0f)
      close()
    },
    color = border.color
  )
}

private fun DrawScope.drawBottom(
  border: BorderLine,
  shareStart: Boolean,
  shareEnd: Boolean,
) {
  val strokeWidthPx = border.strokeWidth.toPx()
  if (strokeWidthPx == 0f) return
  drawPath(
    Path().apply {
      val width = size.width
      val height = size.height
      moveTo(0f, height)
      lineTo(if (shareStart) strokeWidthPx else 0f, height - strokeWidthPx)
      lineTo(if (shareEnd) width - strokeWidthPx else width, height - strokeWidthPx)
      lineTo(width, height)
      close()
    },
    color = border.color
  )
}

private fun DrawScope.drawLeft(
  border: BorderLine,
  shareTop: Boolean = true,
  shareBottom: Boolean = true,
) {
  val strokeWidthPx = border.strokeWidth.toPx()
  if (strokeWidthPx == 0f) return
  drawPath(
    Path().apply {
      moveTo(0f, 0f)
      lineTo(strokeWidthPx, if (shareTop) strokeWidthPx else 0f)
      val height = size.height
      lineTo(strokeWidthPx, if (shareBottom) height - strokeWidthPx else height)
      lineTo(0f, height)
      close()
    },
    color = border.color
  )
}

private fun DrawScope.drawRight(
  border: BorderLine,
  shareTop: Boolean = true,
  shareBottom: Boolean = true,
) {
  val strokeWidthPx = border.strokeWidth.toPx()
  if (strokeWidthPx == 0f) return
  drawPath(
    Path().apply {
      val width = size.width
      val height = size.height
      moveTo(width, 0f)
      lineTo(width - strokeWidthPx, if (shareTop) strokeWidthPx else 0f)
      lineTo(width - strokeWidthPx, if (shareBottom) height - strokeWidthPx else height)
      lineTo(width, height)
      close()
    },
    color = border.color
  )
}
