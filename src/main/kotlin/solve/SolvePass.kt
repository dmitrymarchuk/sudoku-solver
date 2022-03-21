package solve

import solve.engine.SolveStep

interface SolvePass {
  fun solve(): SolveStep.Change.Cells
}
