package com.github.mrzahmadi.lightnote.view.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
import com.github.mrzahmadi.lightnote.ui.theme.isSettingsOnNightMode
import com.github.mrzahmadi.lightnote.ui.theme.navigationBarColor
import com.github.mrzahmadi.lightnote.ui.theme.navigationBarContentColor
import com.github.mrzahmadi.lightnote.ui.theme.navigationBarSelectedContentBadgeColor
import com.github.mrzahmadi.lightnote.ui.theme.navigationBarSelectedContentColor
import com.github.mrzahmadi.lightnote.ui.theme.windowBackgroundColor
import com.github.mrzahmadi.lightnote.utils.ext.getRouteWithoutParams
import com.github.mrzahmadi.lightnote.view.Screen
import com.github.mrzahmadi.lightnote.view.screen.favorite.FavoriteScreen
import com.github.mrzahmadi.lightnote.view.screen.favorite.FavoriteViewModel
import com.github.mrzahmadi.lightnote.view.screen.favorite.selectedFavoriteNoteList
import com.github.mrzahmadi.lightnote.view.screen.home.HomeScreen
import com.github.mrzahmadi.lightnote.view.screen.home.HomeViewModel
import com.github.mrzahmadi.lightnote.view.screen.home.selectedHomeNoteList
import com.github.mrzahmadi.lightnote.view.screen.note.NoteScreen
import com.github.mrzahmadi.lightnote.view.screen.note.NoteViewModel
import com.github.mrzahmadi.lightnote.view.screen.profile.ProfileScreen
import com.github.mrzahmadi.lightnote.view.screen.profile.ProfileViewModel
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity(), ProfileViewModel.ThemeChangeListener {

    private val homeViewModel: HomeViewModel by viewModels()
    private val favoriteViewModel: FavoriteViewModel by viewModels()
    private val profileViewModel: ProfileViewModel by viewModels()
    private val noteViewModel: NoteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        profileViewModel.setThemeChangeListener(this)

        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(
                scrim = Color.Transparent.toArgb(),
            ),
            navigationBarStyle = SystemBarStyle.light(
                scrim = Color.Transparent.toArgb(),
                darkScrim = Color.Transparent.toArgb()
            )
        )

        setContent {
            LightNoteTheme(
                darkTheme = isSettingsOnNightMode(LocalContext.current)
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = windowBackgroundColor(),
                ) {
                    AppNavHost(
                        homeViewModel = homeViewModel,
                        favoriteViewModel = favoriteViewModel,
                        profileViewModel = profileViewModel,
                        noteViewModel = noteViewModel
                    )
                }
            }
        }
    }

    companion object {
        const val NAVIGATION_BAR_ITEM_TAG_PREFIX = "NavigationBarItem_"
    }

    override fun onThemeChanged(theme: Int) {
        recreate()
    }
}

@Preview(showSystemUi = true)
@Composable
fun AppNavHostPreview() {
    AppNavHost()
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    homeViewModel: HomeViewModel? = null,
    favoriteViewModel: FavoriteViewModel? = null,
    profileViewModel: ProfileViewModel? = null,
    noteViewModel: NoteViewModel? = null
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
        contentWindowInsets = WindowInsets(0.dp),
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
            homeViewModel,
            favoriteViewModel,
            profileViewModel,
            noteViewModel
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
        items.forEachIndexed { index, screen ->
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
                modifier = modifier.testTag(MainActivity.NAVIGATION_BAR_ITEM_TAG_PREFIX + index),
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
                    (
                            if (isSelected)
                                screen.selectedIcon
                            else
                                screen.unselectedIcon
                            )?.let {
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
    innerPadding: PaddingValues,
    homeViewModel: HomeViewModel?,
    favoriteViewModel: FavoriteViewModel?,
    profileViewModel: ProfileViewModel?,
    noteViewModel: NoteViewModel?
) {
    val animationDuration = 500
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    NavHost(
        navController,
        startDestination = Screen.Home.route,
        Modifier.padding(innerPadding),
        enterTransition = { fadeIn(tween(animationDuration)) },
        exitTransition = { ExitTransition.None },
        popEnterTransition =  { fadeIn(tween(animationDuration)) },
        popExitTransition = { ExitTransition.None }

        ) {

        // currentRouteChanges
        val currentRoute = navBackStackEntry?.destination?.route
        currentRoute?.let { route ->
            when (route) {

                Screen.Home.route -> {
                    selectedHomeNoteList.clear()
                }

                Screen.Favorite.route -> {
                    selectedFavoriteNoteList.clear()
                }

            }
        }

        // start new routes
        composable(Screen.Home.route) {
            HomeScreen(
                navHostController = navController,
                viewModel = homeViewModel
            )
        }
        composable(Screen.Favorite.route) {
            FavoriteScreen(
                navHostController = navController,
                viewModel = favoriteViewModel
            )
        }
        composable(Screen.Profile.route) {
            ProfileScreen(
                viewModel = profileViewModel
            )
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
            val note = if (noteObject != null) {
                Gson().fromJson(noteObject, Note::class.java)
            } else {
                Note(
                    title = null,
                    description = null,
                ).apply {
                    this.isNew = true
                }
            }
            NoteScreen(
                navHostController = navController,
                note = note,
                viewModel = noteViewModel
            )
        }
    }
}
