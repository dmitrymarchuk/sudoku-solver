package solve.engine.executors

import solve.SolvePassFactory
import model.board.Board
import kotlin.reflect.KClass

class SinglePassExecutor(
  private val factory: SolvePassFactory,
) : PassExecutorBase() {
  override operator fun invoke(board: Board) = factory(board).solve()
  override fun toString() = factory.name
}
