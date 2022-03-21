package solve.engine

import SolvePassFactory

fun SolvePassFactory.checkSolved() = this.toExecutor().checkSolved()

fun PassExecutor.checkSolved() = BoardSolvedCheckExecutor(this)

fun SolvePassFactory.toExecutor() =
  SinglePassExecutor(this).checkSolved()

fun PassExecutor.exhausting() = ExhaustingExecutor(this.checkSolved())

infix fun SolvePassFactory.then(other: PassExecutor): MultiStepExecutor {
  return MultiStepExecutor(this.toExecutor(), other)
}

infix fun SolvePassFactory.combined(other: PassExecutor): CombinedStepExecutor {
  return CombinedStepExecutor(this then other)
}

infix fun SolvePassFactory.then(other: SolvePassFactory): MultiStepExecutor {
  return MultiStepExecutor(this.toExecutor(), other.toExecutor())
}

infix fun SolvePassFactory.combined(other: SolvePassFactory): CombinedStepExecutor {
  return CombinedStepExecutor(this then other)
}

infix fun PassExecutor.then(other: SolvePassFactory): MultiStepExecutor {
  return MultiStepExecutor(this, other.toExecutor())
}

infix fun PassExecutor.combined(other: SolvePassFactory): CombinedStepExecutor {
  return CombinedStepExecutor(this then other)
}

infix fun PassExecutor.then(other: PassExecutor): MultiStepExecutor {
  return MultiStepExecutor(this, other)
}

infix fun PassExecutor.combined(other: PassExecutor): CombinedStepExecutor {
  return CombinedStepExecutor(this then other)
}
