package solve.engine

import solve.engine.executors.BoardSolvedCheckExecutor
import solve.engine.executors.CombinedStepExecutor
import solve.engine.executors.ExhaustingExecutor
import solve.engine.executors.MultiStepExecutor
import solve.engine.executors.PassExecutor
import solve.engine.executors.SinglePassExecutor

class ExecutorToStringSerializer(
  private val e: PassExecutor,
  private val level: Int = 0,
) {
  private fun tabs(level: Int) = "\t".repeat(level)
  override fun toString(): String {
    return when (e) {
      is BoardSolvedCheckExecutor -> "isSolved(${
        ExecutorToStringSerializer(e.executor, level)
      })"
      is CombinedStepExecutor     ->
        multi("Combined", e.multiStepExecutor.executors.toList())
      is ExhaustingExecutor       ->
        levelUp("exhausting", ExecutorToStringSerializer(e.executor, level + 1))
      is MultiStepExecutor        ->
        multi("Multi", e.executors.toList())
      is SinglePassExecutor       ->
        e.toString()
    }
  }

  private fun levelUp(header: String, inner: Any): String {
    return "${header}(\n" +
        tabs(level + 1) +
        inner +
        "\n" + tabs(level) + ")"
  }

  private fun multi(header: String, executors: Iterable<PassExecutor>): String {
    return levelUp(header, executors
      .map { ExecutorToStringSerializer(it, level + 1) }
      .joinToString("\n${tabs(level + 1)}"))
  }
}
