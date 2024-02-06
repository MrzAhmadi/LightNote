package com.github.mrzahmadi.lightnote.view.screen.favorite

import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
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
import com.github.mrzahmadi.lightnote.ui.theme.primaryDarkColor
import com.github.mrzahmadi.lightnote.ui.theme.windowBackgroundColor
import com.github.mrzahmadi.lightnote.utils.ext.showToast
import com.github.mrzahmadi.lightnote.view.Screen
import com.github.mrzahmadi.lightnote.view.widget.BaseAlertDialog
import com.github.mrzahmadi.lightnote.view.widget.BaseTopAppBar
import com.github.mrzahmadi.lightnote.view.widget.NoteItem
import com.github.mrzahmadi.lightnote.view.widget.WatermarkMessage

var selectedFavoriteNoteList = ArrayList<Note>()

@Composable
fun FavoriteScreen(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    viewModel: FavoriteViewModel,
) {

    val state by viewModel.state.collectAsState()

    var isSelectedToolbarEnabled by remember {
        mutableStateOf(
            selectedFavoriteNoteList.isEmpty().not()
        )
    }

    val openDeleteAlertDialog = remember { mutableStateOf(false) }
    if (openDeleteAlertDialog.value) {
        ShowDeleteDialog(
            modifier,
            openDeleteAlertDialog,
        ) {
            viewModel.processIntent(FavoriteViewIntent.DeleteNote(selectedFavoriteNoteList))
            selectedFavoriteNoteList.clear()
            isSelectedToolbarEnabled = false
        }
    }

    // Back press handler
    val onBackPressedDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
    val backPressedHandler = rememberUpdatedState(onBackPressedDispatcher)
    var callback: OnBackPressedCallback? = null
    DisposableEffect(isSelectedToolbarEnabled) {
        if (isSelectedToolbarEnabled) {
            callback = object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    // Check if any items are selected, and if so, clear the selection
                    if (selectedFavoriteNoteList.isNotEmpty()) {
                        selectedFavoriteNoteList.forEach {
                            it.isSelected = false
                            it.isSelectedState.value = false
                        }
                        selectedFavoriteNoteList.clear()
                        isSelectedToolbarEnabled = false

                        isEnabled = false
                        remove()
                    }
                }
            }

            backPressedHandler.value?.addCallback(callback as OnBackPressedCallback)
        }

        onDispose {
            callback?.isEnabled = false
            callback?.remove()
        }
    }

    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        AppBars(isSelectedToolbarEnabled, openDeleteAlertDialog)
    }, content = { paddingValues ->
        ConstraintLayout(
            modifier = modifier
                .fillMaxSize()
                .background(windowBackgroundColor())
                .padding(paddingValues)
        ) {
            val listRefs = createRef()

            when (state) {
                is DataState.Loading -> {
                }

                is DataState.Empty -> {
                    WatermarkMessage(
                        modifier,
                        icon = painterResource(id = R.drawable.t_rex),
                        text = stringResource(id = R.string.no_favorite_note)
                    )
                }

                is DataState.Success -> {
                    val noteList = (state as DataState.Success<List<Note>>).data
                    ShowList(
                        modifier = modifier.constrainAs(listRefs) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        },
                        navHostController = navHostController,
                        noteList = noteList
                    ) {
                        isSelectedToolbarEnabled = selectedFavoriteNoteList.size > 0
                    }
                }

                is DataState.Error -> {
                    val error = (state as DataState.Error).error
                    error.localizedMessage?.let { LocalContext.current.showToast(it) }
                }

            }
        }
    })

    LaunchedEffect(null) {
        viewModel.processIntent(FavoriteViewIntent.GetFavoriteNoteList)
    }

    DisposableEffect(null) {
        onDispose {
            selectedFavoriteNoteList.forEach {
                it.isSelected = false
                it.isSelectedState.value = false
            }
            selectedFavoriteNoteList.clear()
            callback?.isEnabled = false
            callback?.remove()
            isSelectedToolbarEnabled = false
        }
    }
}

@Composable
private fun AppBars(
    isSelectedToolbarEnabled: Boolean,
    openDeleteAlertDialog: MutableState<Boolean>
) {
    if (isSelectedToolbarEnabled.not())
        BaseTopAppBar(title = stringResource(id = Screen.Favorite.title))
    else
        BaseTopAppBar(
            title = stringResource(id = Screen.Favorite.title),
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
        dialogText = if (selectedFavoriteNoteList.size > 1)
            stringResource(
                id = R.string.delete_note_dialog_text_count,
                selectedFavoriteNoteList.size
            )
        else
            stringResource(id = R.string.delete_note_dialog_text),
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
            horizontal = 16.dp, vertical = 20.dp
        )
    ) {
        items(items = noteList,
            key = { it.id },
            itemContent = { lazyItem ->
                NoteItem(
                    modifier = Modifier,
                    note = lazyItem,
                    navHostController = navHostController,
                    changeSelected = {
                        selectedFavoriteNoteList.clear()
                        noteList.forEach {
                            if (it.isSelected)
                                selectedFavoriteNoteList.add(it)
                        }
                        changeSelectedStatus()
                    },
                    checkOtherItemsSelected = {
                        val selectedList = noteList.filter {
                            it.isSelected
                        }
                        selectedList.isNotEmpty()
                    },
                    isSelected = remember {
                        lazyItem.isSelectedState
                    }
                )
            })
    }
}

@Preview(showSystemUi = true)
@Composable
fun FavoriteScreenPreview() {
    val navController: NavHostController = rememberNavController()
    FavoriteScreen(
        navHostController = navController, viewModel = viewModel()
    )
}