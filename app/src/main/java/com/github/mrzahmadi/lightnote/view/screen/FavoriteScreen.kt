package com.github.mrzahmadi.lightnote.view.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import com.github.mrzahmadi.lightnote.view.Screen
import com.github.mrzahmadi.lightnote.view.widget.BaseTopAppBar

@Preview(showSystemUi = true)
@Composable
fun FavoriteScreen(modifier: Modifier = Modifier) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            BaseTopAppBar(title = stringResource(Screen.Favorite.title))
        }, content = {
            it.calculateTopPadding()
            it.calculateBottomPadding()
            it.calculateLeftPadding(layoutDirection = LayoutDirection.Ltr)
            it.calculateRightPadding(layoutDirection = LayoutDirection.Ltr)
        })
}