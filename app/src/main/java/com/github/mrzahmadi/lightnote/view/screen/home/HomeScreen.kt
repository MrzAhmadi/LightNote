package com.github.mrzahmadi.lightnote.view.screen.home

import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
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

var selectedHomeNoteList = ArrayList<Note>()

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    viewModel: HomeViewModel?,
) {

    val state: State<DataState<List<Note>>>? = viewModel?.state?.collectAsState()

    var isSelectedToolbarEnabled by remember {
        mutableStateOf(
            selectedHomeNoteList.isEmpty().not()
        )
    }

    val openDeleteAlertDialog = remember { mutableStateOf(false) }
    if (openDeleteAlertDialog.value) {
        ShowDeleteDialog(
            modifier,
            openDeleteAlertDialog,
        ) {
            viewModel?.processIntent(HomeViewIntent.DeleteNote(selectedHomeNoteList))
            selectedHomeNoteList.clear()
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
                    if (selectedHomeNoteList.isNotEmpty()) {
                        selectedHomeNoteList.forEach {
                            it.isSelectedState.value = false
                        }
                        selectedHomeNoteList.clear()
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

                when (state?.value) {
                    is DataState.Loading -> {
                    }

                    is DataState.Empty -> {
                        WatermarkMessage(
                            modifier,
                            icon = painterResource(id = R.drawable.t_rex),
                            text = stringResource(id = R.string.empty_note)
                        )
                    }

                    is DataState.Success -> {
                        val noteList = (state.value as DataState.Success<List<Note>>).data
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
                            isSelectedToolbarEnabled = selectedHomeNoteList.size > 0
                        }
                    }

                    is DataState.Error -> {
                        val error = (state.value as DataState.Error).error
                        error.localizedMessage?.let { LocalContext.current.showToast(it) }
                    }

                    null -> {}
                }

                var isScaled by remember {
                    mutableStateOf(false)
                }
                val scale by animateFloatAsState(
                    targetValue = if (isScaled) 1.2f else 1f,
                    label = "rotationAngle",
                    animationSpec = tween(200),
                    finishedListener = {
                        if (isScaled)
                            isScaled = false
                        else if (isScaled.not())
                            navHostController.navigate(Screen.Note.route)
                    }
                )
                FloatingActionButton(
                    modifier = modifier
                        .graphicsLayer {
                            scaleX = scale
                            scaleY = scale
                        }
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
                        isScaled = !isScaled
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
        viewModel?.processIntent(HomeViewIntent.GetNoteList)
    }

    DisposableEffect(null) {
        onDispose {
            selectedHomeNoteList.forEach {
                it.isSelectedState.value = false
            }
            selectedHomeNoteList.clear()
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
        title = stringResource(id = R.string.delete_note_dialog_title),
        text = if (selectedHomeNoteList.size > 1)
            stringResource(
                R.string.delete_note_dialog_text_count,
                selectedHomeNoteList.size
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
                        selectedHomeNoteList.clear()
                        selectedHomeNoteList = ArrayList(
                            noteList.filter {
                                it.isSelectedState.value
                            }
                        )
                        changeSelectedStatus()
                    },
                    checkOtherItemsSelected = {
                        val selectedList = noteList.filter {
                            it.isSelectedState.value
                        }
                        selectedList.isNotEmpty()
                    },
                    isSelected = remember {
                        lazyItem.isSelectedState
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