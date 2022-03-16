package solve

import solve.engine.SolveStep

interface SolvePass: AutoCloseable {
  fun execute(): SolveStep.Change?
}
