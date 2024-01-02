package com.github.mrzahmadi.lightnote.view.widget

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun BaseAppBar(modifier: Modifier = Modifier, title: String = "Title") {
    TopAppBar(
        modifier = modifier.shadow(12.dp, spotColor = Color.DarkGray),
        title = { Text(text = title) }
    )
}