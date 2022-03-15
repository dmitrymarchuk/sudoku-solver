package solve

import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

object WarningService {
  fun raiseWarning(warning: Warning) {
    logger.warn { warning }
  }
}

enum class Warning {
  AllPossibleCellsAreCrossedOut
}
