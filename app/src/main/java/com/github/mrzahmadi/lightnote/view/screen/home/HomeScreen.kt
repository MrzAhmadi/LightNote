package com.github.mrzahmadi.lightnote.view.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.github.mrzahmadi.lightnote.R
import com.github.mrzahmadi.lightnote.data.DataState
import com.github.mrzahmadi.lightnote.data.model.Note
import com.github.mrzahmadi.lightnote.ui.theme.primaryBlueColor
import com.github.mrzahmadi.lightnote.ui.theme.primaryDarkColor
import com.github.mrzahmadi.lightnote.ui.theme.whiteColor
import com.github.mrzahmadi.lightnote.ui.theme.windowBackgroundColor
import com.github.mrzahmadi.lightnote.utils.ext.showToast
import com.github.mrzahmadi.lightnote.view.Screen
import com.github.mrzahmadi.lightnote.view.widget.BaseAlertDialog
import com.github.mrzahmadi.lightnote.view.widget.BaseTopAppBar
import com.github.mrzahmadi.lightnote.view.widget.NoteItem
import com.github.mrzahmadi.lightnote.view.widget.WatermarkMessage

var selectedNoteList = ArrayList<Note>()

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    viewModel: HomeViewModel,
) {

    val state by viewModel.state.collectAsState()

    var isSelectedToolbarEnabled by remember { mutableStateOf(false) }

    val openDeleteAlertDialog = remember { mutableStateOf(false) }
    if (openDeleteAlertDialog.value) {
        ShowDeleteDialog(
            modifier,
            openDeleteAlertDialog,
        ) {
            viewModel.processIntent(HomeViewIntent.DeleteNote(selectedNoteList))
            selectedNoteList.clear()
            isSelectedToolbarEnabled = false
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AppBars(isSelectedToolbarEnabled, openDeleteAlertDialog)
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

                    is DataState.Empty -> {
                        WatermarkMessage(
                            modifier,
                            text = stringResource(id = R.string.empty_note),
                            icon = Icons.Filled.Face
                        )
                    }

                    is DataState.Success -> {
                        val noteList = (state as DataState.Success<List<Note>>).data
                        ShowList(
                            modifier = modifier
                                .constrainAs(listRefs) {
                                    top.linkTo(parent.top)
                                    bottom.linkTo(parent.bottom)
                                    start.linkTo(parent.start)
                                    end.linkTo(parent.end)
                                },
                            navHostController = navHostController,
                            noteList = noteList
                        ) {
                            isSelectedToolbarEnabled = selectedNoteList.size > 0
                        }
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

    LaunchedEffect(null) {
        viewModel.processIntent(HomeViewIntent.GetNoteList)
    }
}

@Composable
private fun AppBars(
    isSelectedToolbarEnabled: Boolean,
    openDeleteAlertDialog: MutableState<Boolean>
) {
    if (isSelectedToolbarEnabled.not())
        BaseTopAppBar(title = stringResource(id = Screen.Home.title))
    else
        BaseTopAppBar(
            title = stringResource(id = Screen.Home.title),
            actions = {
                IconButton(onClick = {
                    openDeleteAlertDialog.value = true
                }) {
                    Icon(
                        imageVector =
                        Icons.Filled.Delete,
                        contentDescription = null,
                        tint = primaryDarkColor()
                    )
                }
            }
        )
}

@Composable
private fun ShowDeleteDialog(
    modifier: Modifier,
    openDeleteAlertDialog: MutableState<Boolean>,
    onConfirm: () -> Unit
) {
    BaseAlertDialog(
        modifier = modifier,
        dialogTitle = stringResource(id = R.string.delete_note_dialog_title),
        dialogText = if (selectedNoteList.size > 1)
            stringResource(
                R.string.delete_note_dialog_text_count,
                selectedNoteList.size
            )
        else
            stringResource(R.string.delete_note_dialog_text),
        onDismissRequest = {
            openDeleteAlertDialog.value = false

        },
        onConfirmation = {
            openDeleteAlertDialog.value = false
            onConfirm()
        }
    )
}


@Composable
private fun ShowList(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    noteList: List<Note>,
    changeSelectedStatus: () -> Unit
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
                    modifier,
                    note = lazyItem,
                    navHostController = navHostController,
                    changeSelected = {
                        selectedNoteList.clear()
                        selectedNoteList = ArrayList(
                            noteList.filter {
                                it.isSelected
                            }
                        )
                        changeSelectedStatus()
                    },
                    checkOtherItemsSelected = {
                        val selectedList = noteList.filter {
                            it.isSelected
                        }
                        selectedList.isNotEmpty()
                    }
                )
            }
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    val navController: NavHostController = rememberNavController()
    HomeScreen(
        navHostController = navController,
        viewModel = viewModel()
    )
}