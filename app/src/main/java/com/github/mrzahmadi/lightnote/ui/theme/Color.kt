package com.github.mrzahmadi.lightnote.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

val PrimaryDay = Color(0xFF0B79E5)
val SecondaryDay = Color(0xFFFFFFFF)
val TertiaryDay = Color(0xFF0B79E5)
val BackgroundDay = Color(0xFFFFFFFF)

val PrimaryNight = Color(0xFF8AB4F8)
val SecondaryNight = Color(0xFF202125)
val TertiaryNight = Color(0xFF8AB4F8)
val BackgroundNight = Color(0xFF131313)

@Composable
fun windowBackgroundColor() = if (isSettingsOnNightMode(LocalContext.current))
    BackgroundNight
else
    BackgroundDay

@Composable
fun statusBarColor() = if (isSettingsOnNightMode(LocalContext.current))
    Color(0xFF202125)
else
    Color(0xFFFFFFFF)

@Composable
fun appBarColor() = if (isSettingsOnNightMode(LocalContext.current))
    Color(0xFF131313)
else
    Color(0xFFFFFFFF)

@Composable
fun appBarDividerColor() = if (isSettingsOnNightMode(LocalContext.current))
    Color(0XFF2D2D2D)
else
    Color(0XFFE4E4E4)

@Composable
fun navigationBarColor() = if (isSettingsOnNightMode(LocalContext.current))
    Color(0XFF1E1F21)
else
    Color(0XFFF0F3F8)

@Composable
fun navigationBarContentColor() = if (isSettingsOnNightMode(LocalContext.current))
    Color(0XFFC6C8C7)
else
    Color(0xFF474747)

@Composable
fun navigationBarSelectedContentColor() = if (isSettingsOnNightMode(LocalContext.current))
    Color(0XFFE4E2E3)
else
    Color(0XFF1F1F1F)

@Composable
fun navigationBarSelectedContentBadgeColor() = if (isSettingsOnNightMode(LocalContext.current))
    Color(0XFF004A77)
else
    Color(0XFFC3E7FF)

@Composable
fun cardBackgroundColor() = if (isSettingsOnNightMode(LocalContext.current))
    Color(0xFF202125)
else
    Color(0xFFFFFFFF)


@Composable
fun dividerColor() = if (isSettingsOnNightMode(LocalContext.current))
    Color(0xFF505155)
else
    Color(0XFFDCDCDC)


@Composable
fun primaryDarkColor() = if (isSettingsOnNightMode(LocalContext.current))
    Color(0xFFFFFFFF)
else
    Color(0xFF202124)


@Composable
fun grayColor() = if (isSettingsOnNightMode(LocalContext.current))
    Color(0xFFE8EAED)
else
    Color(0xFF3C4043)

@Composable
fun softGrayColor() = if (isSettingsOnNightMode(LocalContext.current))
    Color(0xFFA0A3A7)
else
    Color(0xFF5F6368)

@Composable
fun lightGrayColor() = if (isSettingsOnNightMode(LocalContext.current))
    Color(0xFFA0A3A7)
else
    Color(0xFFA0A3A7)

@Composable
fun lightColor() = if (isSettingsOnNightMode(LocalContext.current))
    Color(0xFF3C4043)
else
    Color(0xFFF8F9FA)

@Composable
fun whiteColor() = if (isSettingsOnNightMode(LocalContext.current))
    Color(0xFF1A1B1E)
else
    Color(0xFFFFFFFF)

@Composable
fun skyColor() = if (isSettingsOnNightMode(LocalContext.current))
    Color(0xFF3C434E)
else
    Color(0xFFE8F0FB)

@Composable
fun lightBlueColor() = if (isSettingsOnNightMode(LocalContext.current))
    Color(0xFF8AB4F8)
else
    Color(0xFF8AB4F8)

@Composable
fun primaryBlueColor() = if (isSettingsOnNightMode(LocalContext.current))
    Color(0xFF8AB4F8)
else
    Color(0xFF0B79E5)

@Composable
fun redColor() = if (isSettingsOnNightMode(LocalContext.current))
    Color(0xFFE65C73)
else
    Color(0xFFFF4C6A)

@Composable
fun greenColor() = if (isSettingsOnNightMode(LocalContext.current))
    Color(0xFF6BB374)
else
    Color(0xFF4ECC5E)

@Composable
fun yellowColor() = if (isSettingsOnNightMode(LocalContext.current))
    Color(0xFFFAA805)
else
    Color(0xFFFAA805)

@Composable
fun pinkColor() = if (isSettingsOnNightMode(LocalContext.current))
    Color(0xFFFF70A2)
else
    Color(0xFFFF70A2)
