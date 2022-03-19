package solve

import solve.engine.SolveStep

interface SolvePass {
  fun execute(): SolveStep.Change
}
