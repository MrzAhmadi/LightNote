package com.github.mrzahmadi.lightnote.view.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSavedStateRegistryOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.github.mrzahmadi.lightnote.data.DataState
import com.github.mrzahmadi.lightnote.data.db.DatabaseBuilder
import com.github.mrzahmadi.lightnote.data.model.Note
import com.github.mrzahmadi.lightnote.data.repository.NoteRepository
import com.github.mrzahmadi.lightnote.ui.theme.primaryBlueColor
import com.github.mrzahmadi.lightnote.ui.theme.whiteColor
import com.github.mrzahmadi.lightnote.ui.theme.windowBackgroundColor
import com.github.mrzahmadi.lightnote.utils.ext.showToast
import com.github.mrzahmadi.lightnote.view.Screen
import com.github.mrzahmadi.lightnote.view.widget.BaseTopAppBar
import com.github.mrzahmadi.lightnote.view.widget.NoteItem

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    homeViewModel: HomeViewModel,
) {

    val state by homeViewModel.state.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            BaseTopAppBar(title = stringResource(id = Screen.Home.title))
        }, content = { paddingValues ->
            ConstraintLayout(
                modifier = modifier
                    .fillMaxSize()
                    .background(windowBackgroundColor())
                    .padding(paddingValues)
            ) {
                val (floatingRefs, listRefs) = createRefs()

                when (state) {
                    is DataState.Loading -> {
                    }

                    is DataState.Success -> {
                        ShowList(
                            modifier = modifier
                                .constrainAs(listRefs) {
                                    top.linkTo(parent.top)
                                    bottom.linkTo(parent.bottom)
                                    start.linkTo(parent.start)
                                    end.linkTo(parent.end)
                                },
                            navHostController = navHostController,
                            noteList = (state as DataState.Success<List<Note>>).data
                        )
                    }

                    is DataState.Error -> {
                        val error = (state as DataState.Error).error
                        error.localizedMessage?.let { LocalContext.current.showToast(it) }
                    }

                }

                FloatingActionButton(
                    modifier = modifier
                        .constrainAs(floatingRefs) {
                            bottom.linkTo(parent.bottom, 16.dp)
                            end.linkTo(parent.end, 16.dp)
                        },
                    elevation = FloatingActionButtonDefaults.elevation(
                        defaultElevation = 6.dp,
                        focusedElevation = 12.dp,
                        pressedElevation = 8.dp,
                        hoveredElevation = 8.dp
                    ),
                    contentColor = whiteColor(),
                    containerColor = primaryBlueColor(),
                    onClick = {
                        navHostController.navigate(Screen.Note.route)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "add"
                    )
                }
            }
        })

    homeViewModel.processIntent(HomeViewIntent.FetchNoteList)
}

@Composable
private fun ShowList(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    noteList: List<Note>
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(
            horizontal = 16.dp,
            vertical = 20.dp
        )
    ) {
        items(
            items = noteList,
            key = { it.id },
            itemContent = { lazyItem ->
                NoteItem(
                    note = lazyItem,
                    navHostController = navHostController
                )
            }
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    val navController: NavHostController = rememberNavController()
    val dao = DatabaseBuilder.getInstance(LocalContext.current).noteDao()
    val noteRepository = NoteRepository(dao)
    HomeScreen(
        navHostController = navController,
        homeViewModel = viewModel(
            factory = HomeViewModel.provideFactory(
                noteRepository = noteRepository,
                owner = LocalSavedStateRegistryOwner.current
            )
        )
    )
}