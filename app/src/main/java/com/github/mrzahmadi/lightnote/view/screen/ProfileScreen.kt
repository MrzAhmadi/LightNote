package com.github.mrzahmadi.lightnote.view.screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.github.mrzahmadi.lightnote.view.Screen

@Preview(showSystemUi = true)
@Composable
fun ProfileScreen() {
    Text(text = stringResource(Screen.Profile.title))
}