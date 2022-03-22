package solve.engine

import solve.engine.executors.ExhaustingExecutor
import solve.engine.executors.MultiStepExecutor
import solve.engine.executors.PassExecutor
import solve.engine.executors.SinglePassExecutor

class ExecutorToStringSerializer(
  private val e: PassExecutor,
  private val level: Int = 0,
) {
  private fun tabs(level: Int) = "\t".repeat(level)

  @Suppress("REDUNDANT_ELSE_IN_WHEN")
  override fun toString(): String {
    return when (e) {
      is ExhaustingExecutor ->
        levelUp("exhausting", ExecutorToStringSerializer(e.executor, level + 1))
      is MultiStepExecutor  ->
        multi(e.executors)
      is SinglePassExecutor ->
        e.toString()
      else                  ->
        throw IllegalStateException()
    }
  }

  private fun levelUp(header: String, inner: Any): String {
    return "${header}(\n" +
        tabs(level + 1) +
        inner +
        "\n" + tabs(level) + ")"
  }

  private fun multi(executors: Array<out PassExecutor>): String {
    return levelUp("Multi", executors
      .map { ExecutorToStringSerializer(it, level + 1) }
      .joinToString("\n${tabs(level + 1)}"))
  }
}
