package solve.pass

import com.github.shiguruikai.combinatoricskt.Combinatorics.combinations
import com.github.shiguruikai.combinatoricskt.combinations
import model.board.Board
import model.board.HouseType
import model.cell.Cell
import mu.KotlinLogging
import solve.EachHouseSolvePass
import util.toIndexed

private val logger = KotlinLogging.logger {}

class NakedSet(
  type: HouseType,
  private val size: Int,
  initialBoard: Board,
) : EachHouseSolvePass(type, initialBoard) {
  override fun prepare() {
    logger.info {
      "Looking for naked-single cell sets " +
          "in ${type.toString().lowercase()}s"
    }
  }

  override fun transformHouse(cells: List<Cell>): List<Cell> {
    val pairs = cells
      .toIndexed()
      .combinations(size)
      .map { (first, second) -> Pair(first, second) }
    pairs.forEach { (first, second) ->
      TODO()
    }
    TODO()
  }
}
