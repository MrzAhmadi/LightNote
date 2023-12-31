package com.github.mrzahmadi.lightnote.view

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.github.mrzahmadi.lightnote.R

sealed class Screen(
    @StringRes val title: Int,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val route: String
) {

    data object Home : Screen(
        R.string.Home,
        Icons.Filled.Home,
        Icons.Outlined.Home,
        "Home"
    )

    data object Favorite : Screen(
        R.string.favorite,
        Icons.Filled.Favorite,
        Icons.Outlined.FavoriteBorder,
        "favorite"
    )

    data object Profile : Screen(
        R.string.profile,
        Icons.Filled.AccountCircle,
        Icons.Outlined.AccountCircle,
        "profile"
    )

    companion object {
        fun getNavigationBarScreenList(): List<Screen> {
            return listOf(
                Home,
                Favorite,
                Profile
            )
        }
    }

}
