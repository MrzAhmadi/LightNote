package com.github.mrzahmadi.lightnote.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val PrimaryDay = Color(0xFF0B79E5)
val SecondaryDay = Color(0xFFFFFFFF)
val TertiaryDay = Color(0xFF0B79E5)
val BackgroundDay = Color(0xFFFFFFFF)

val PrimaryNight = Color(0xFF8AB4F8)
val SecondaryNight = Color(0xFF202125)
val TertiaryNight = Color(0xFF8AB4F8)
val BackgroundNight = Color(0xFF202125)



@Composable
fun windowBackgroundColor() = if (isSystemInDarkTheme())
    BackgroundNight
else
    BackgroundDay


@Composable
fun appBarColor() = if (isSystemInDarkTheme())
    Color(0xFF202125)
else
    Color(0xFFFFFFFF)


@Composable
fun appBarDividerColor() = if (isSystemInDarkTheme())
    Color(0xFF505155)
else
    Color(0XFFDCDCDC)


@Composable
fun cardBackgroundColor() = if (isSystemInDarkTheme())
    Color(0XFF3C4043)
else
    Color(0xFFFFFFFF)


@Composable
fun dividerColor() = if(isSystemInDarkTheme())
    Color(0xFF505155)
else
    Color(0XFFDCDCDC)


@Composable
fun primaryDarkColor() = if(isSystemInDarkTheme())
    Color(0xFFFFFFFF)
else
    Color(0xFF202124)


@Composable
fun grayColor() = if(isSystemInDarkTheme())
    Color(0xFFE8EAED)
else
    Color(0xFF3C4043)

@Composable
fun softGrayColor() = if(isSystemInDarkTheme())
    Color(0xFFA0A3A7)
else
    Color(0xFF5F6368)

@Composable
fun lightGrayColor() = if(isSystemInDarkTheme())
    Color(0xFFA0A3A7)
else
    Color(0xFFA0A3A7)

@Composable
fun lightColor() = if(isSystemInDarkTheme())
    Color(0xFF3C4043)
else
    Color(0xFFF8F9FA)

@Composable
fun whiteColor() = if(isSystemInDarkTheme())
    Color(0xFF1A1B1E)
else
    Color(0xFFFFFFFF)

@Composable
fun skyColor() = if(isSystemInDarkTheme())
    Color(0xFF3C434E)
else
    Color(0xFFE8F0FB)

@Composable
fun lightBlueColor() = if(isSystemInDarkTheme())
    Color(0xFF8AB4F8)
else
    Color(0xFF8AB4F8)

@Composable
fun primaryBlueColor() = if(isSystemInDarkTheme())
    Color(0xFF8AB4F8)
else
    Color(0xFF0B79E5)

@Composable
fun redColor() = if(isSystemInDarkTheme())
    Color(0xFFE65C73)
else
    Color(0xFFFF4C6A)

@Composable
fun greenColor() = if(isSystemInDarkTheme())
    Color(0xFF6BB374)
else
    Color(0xFF4ECC5E)

@Composable
fun yellowColor() = if(isSystemInDarkTheme())
    Color(0xFFFAA805)
else
    Color(0xFFFAA805)

@Composable
fun pinkColor() = if(isSystemInDarkTheme())
    Color(0xFFFF70A2)
else
    Color(0xFFFF70A2)
