package com.github.mrzahmadi.lightnote.view.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.github.mrzahmadi.lightnote.ui.theme.windowBackgroundColor
import com.github.mrzahmadi.lightnote.view.Screen
import com.github.mrzahmadi.lightnote.view.widget.BaseTopAppBarWithBack

@Composable
fun NoteScreen(
    modifier: Modifier = Modifier,
    navHostController: NavHostController
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            BaseTopAppBarWithBack(
                title = stringResource(Screen.Note.title),
                onClick = {
                    navHostController.popBackStack()
                }
            )
        }, content = { paddingValues ->
            Column(
                modifier
                    .fillMaxSize()
                    .fillMaxWidth()
                    .padding(paddingValues)
                    .background(windowBackgroundColor())
            ) {
            }
        })
}

@Preview(showSystemUi = true)
@Composable
fun NoteScreenPreview() {
    val navController: NavHostController = rememberNavController()
    NoteScreen(
        navHostController = navController
    )
}