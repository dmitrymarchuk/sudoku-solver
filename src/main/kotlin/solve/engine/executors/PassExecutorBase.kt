package solve.engine.executors

import solve.engine.ExecutorToStringSerializer

sealed class PassExecutorBase : PassExecutor {
  override fun toString(): String {
    return ExecutorToStringSerializer(this).toString()
  }
}
