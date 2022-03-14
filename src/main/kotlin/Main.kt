// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import model.ui.BoardModel
import model.parse.loadCsv
import preview.sampleBoard
import ui.board.UiBoard

@Composable
@Preview
fun App(board: BoardModel = sampleBoard) {
  MaterialTheme {
    UiBoard(board)
  }
}

fun main() = application {
  val list = loadCsv().iterator()
  Window(onCloseRequest = ::exitApplication) {
    var board by remember { mutableStateOf(list.next()) }
    Box(
      modifier =
      Modifier.widthIn(min = 400.dp, max = 800.dp)
        .clickable { board = list.next() }
    ) {
      App(board)
    }
  }
}
