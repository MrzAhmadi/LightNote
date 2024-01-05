package com.github.mrzahmadi.lightnote.view.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.github.mrzahmadi.lightnote.ui.theme.windowBackgroundColor
import com.github.mrzahmadi.lightnote.view.Screen
import com.github.mrzahmadi.lightnote.view.widget.BaseTopAppBar

@Preview(showSystemUi = true)
@Composable
fun FavoriteScreen(modifier: Modifier = Modifier) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(windowBackgroundColor()),
        topBar = {
            BaseTopAppBar(title = stringResource(Screen.Favorite.title))
        }, content = { paddingValues ->
            Column(
                modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(windowBackgroundColor())
            ) {

            }
        })
}