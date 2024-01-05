package com.github.mrzahmadi.lightnote.view.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.github.mrzahmadi.lightnote.ui.theme.LightNoteTheme
import com.github.mrzahmadi.lightnote.ui.theme.appBarColor
import com.github.mrzahmadi.lightnote.ui.theme.appBarDividerColor
import com.github.mrzahmadi.lightnote.ui.theme.grayColor
import com.github.mrzahmadi.lightnote.ui.theme.softGrayColor
import com.github.mrzahmadi.lightnote.ui.theme.windowBackgroundColor
import com.github.mrzahmadi.lightnote.view.Screen
import com.github.mrzahmadi.lightnote.view.screen.FavoriteScreen
import com.github.mrzahmadi.lightnote.view.screen.HomeScreen
import com.github.mrzahmadi.lightnote.view.screen.ProfileScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LightNoteTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = windowBackgroundColor()
                ) {
                    NaveHost()
                }
            }
        }
    }
}

const val NAVIGATION_BAR_ITEM_TAG_PREFIX = "NavigationBarItem_"

@OptIn(ExperimentalComposeUiApi::class)
@Preview(showSystemUi = true)
@Composable
fun NaveHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val items = Screen.getNavigationBarScreenList()
    Scaffold(
        modifier = modifier.semantics {
            testTagsAsResourceId = true
        },
        bottomBar = {
            PrimaryNavigationBar(navController, items, modifier)
            Spacer(
                modifier = modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(appBarDividerColor())
            )
        }
    ) { innerPadding ->
        PrimaryNaveHost(navController, innerPadding)
    }

}

@Composable
private fun PrimaryNavigationBar(
    navController: NavHostController,
    items: List<Screen>,
    modifier: Modifier
) {
    NavigationBar(
        containerColor = appBarColor()
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        items.forEachIndexed() { index, screen ->
            val isSelected =
                currentDestination?.hierarchy?.any { it.route == screen.route } == true
            NavigationBarItem(
                modifier = modifier.testTag(NAVIGATION_BAR_ITEM_TAG_PREFIX + index),
                alwaysShowLabel = false,
                label = { Text(stringResource(screen.title)) },
                selected = isSelected,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    if (isSelected)
                        Icon(
                            imageVector = screen.selectedIcon,
                            contentDescription = screen.route,
                            tint = grayColor()
                        )
                    else
                        Icon(
                            imageVector = screen.unselectedIcon,
                            contentDescription = screen.route,
                            tint = softGrayColor()
                        )
                }
            )
        }
    }
}

@Composable
private fun PrimaryNaveHost(
    navController: NavHostController,
    innerPadding: PaddingValues
) {
    NavHost(
        navController,
        startDestination = Screen.Home.route,
        Modifier.padding(innerPadding),
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {
        composable(Screen.Home.route) {
            HomeScreen()
        }
        composable(Screen.Favorite.route) {
            FavoriteScreen()
        }
        composable(Screen.Profile.route) {
            ProfileScreen()
        }
    }
}