package com.github.mrzahmadi.lightnote.view.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.github.mrzahmadi.lightnote.data.model.Note
import com.github.mrzahmadi.lightnote.ui.theme.LightNoteTheme
import com.github.mrzahmadi.lightnote.ui.theme.navigationBarColor
import com.github.mrzahmadi.lightnote.ui.theme.navigationBarContentColor
import com.github.mrzahmadi.lightnote.ui.theme.navigationBarSelectedContentBadgeColor
import com.github.mrzahmadi.lightnote.ui.theme.navigationBarSelectedContentColor
import com.github.mrzahmadi.lightnote.ui.theme.windowBackgroundColor
import com.github.mrzahmadi.lightnote.utils.ext.getRouteWithoutParams
import com.github.mrzahmadi.lightnote.view.Screen
import com.github.mrzahmadi.lightnote.view.screen.FavoriteScreen
import com.github.mrzahmadi.lightnote.view.screen.HomeScreen
import com.github.mrzahmadi.lightnote.view.screen.NoteScreen
import com.github.mrzahmadi.lightnote.view.screen.ProfileScreen
import com.google.gson.Gson

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
    val bottomBarState = remember { mutableStateOf(true) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    when (navBackStackEntry?.destination?.getRouteWithoutParams()) {
        Screen.Note.route -> {
            bottomBarState.value = false
        }

        else -> {
            bottomBarState.value = true
        }
    }
    val items = Screen.getNavigationBarScreenList()
    Scaffold(
        modifier = modifier.semantics {
            testTagsAsResourceId = true
        },
        bottomBar = {
            if (bottomBarState.value)
                PrimaryNavigationBar(
                    navController,
                    items,
                    modifier
                )
        }
    ) { innerPadding ->
        PrimaryNaveHost(
            navController,
            innerPadding,
        )
    }

}

@Composable
private fun PrimaryNavigationBar(
    navController: NavHostController,
    items: List<Screen>,
    modifier: Modifier
) {
    NavigationBar(
        containerColor = navigationBarColor(),
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        items.forEachIndexed() { index, screen ->
            val isSelected =
                currentDestination?.hierarchy?.any { it.route == screen.route } == true
            NavigationBarItem(
                colors = NavigationBarItemDefaults.colors(
                    disabledIconColor = navigationBarContentColor(),
                    disabledTextColor = navigationBarContentColor(),
                    unselectedIconColor = navigationBarContentColor(),
                    unselectedTextColor = navigationBarContentColor(),
                    selectedIconColor = navigationBarSelectedContentColor(),
                    selectedTextColor = navigationBarSelectedContentColor(),
                    indicatorColor = navigationBarSelectedContentBadgeColor()
                ),
                modifier = modifier.testTag(NAVIGATION_BAR_ITEM_TAG_PREFIX + index),
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
                        screen.selectedIcon?.let {
                            Icon(
                                imageVector = it,
                                contentDescription = screen.route,
                            )
                        }
                    else
                        screen.unselectedIcon?.let {
                            Icon(
                                imageVector = it,
                                contentDescription = screen.route,
                            )
                        }
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
            HomeScreen(navHostController = navController)
        }
        composable(Screen.Favorite.route) {
            FavoriteScreen()
        }
        composable(Screen.Profile.route) {
            ProfileScreen()
        }
        composable(
            "${Screen.Note.route}?{noteObject}",
            arguments = listOf(navArgument("noteObject") {
                nullable = true
                defaultValue = null
                type = NavType.StringType
            }),
        ) { navBackStackEntry ->
            val noteObject = navBackStackEntry.arguments?.getString("noteObject")
            val note = if(noteObject!=null){
                Gson().fromJson(noteObject, Note::class.java)
            } else
                null
            NoteScreen(
                navHostController = navController,
                note = note
            )
        }
    }
}