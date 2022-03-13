package model

class MultiCell(
  val subCells: Grid3x3.Simple<SubCell>,
) : Cell(),
    Grid3x3.Simple<SubCell>,
    List<SubCell> by subCells {
  init {
    Grid3x3.assertSize(subCells)
  }
}
