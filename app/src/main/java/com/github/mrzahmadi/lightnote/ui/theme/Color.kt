package com.github.mrzahmadi.lightnote.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)



@Composable
fun backgroundColor() = if (isSystemInDarkTheme())
    Color.DarkGray
else
    Color.White

@Composable
fun titleColor() = if (isSystemInDarkTheme())
    Color.White
else
    Color.Black

@Composable
fun descriptionColor() = if (isSystemInDarkTheme())
    Color.DarkGray
else
    Color.Gray

@Composable
fun cardBackgroundColor() = if (isSystemInDarkTheme())
    Color.Gray
else
    Color.White