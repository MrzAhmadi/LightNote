package com.github.mrzahmadi.lightnote.view.screen.note

import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.github.mrzahmadi.lightnote.R
import com.github.mrzahmadi.lightnote.data.model.Note
import com.github.mrzahmadi.lightnote.ui.theme.grayColor
import com.github.mrzahmadi.lightnote.ui.theme.primaryDarkColor
import com.github.mrzahmadi.lightnote.ui.theme.softGrayColor
import com.github.mrzahmadi.lightnote.ui.theme.windowBackgroundColor
import com.github.mrzahmadi.lightnote.utils.DAY_GROUP_PREVIEW
import com.github.mrzahmadi.lightnote.utils.NIGHT_GROUP_PREVIEW
import com.github.mrzahmadi.lightnote.view.Screen
import com.github.mrzahmadi.lightnote.view.widget.BaseAlertDialog
import com.github.mrzahmadi.lightnote.view.widget.BaseTopAppBar


private const val TITLE_MAX_LENGTH = 50
private const val DESCRIPTION_MAX_LENGTH = 10000

@Composable
fun NoteScreen(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    note: Note,
    viewModel: NoteViewModel? = null,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
) {

    val mNote by remember { derivedStateOf { note } }
    val isFavorite = remember { mutableStateOf(note.isFavorite) }

    val openDeleteAlertDialog = remember { mutableStateOf(false) }
    if (openDeleteAlertDialog.value) {
        ShowDeleteDialog(modifier, openDeleteAlertDialog, viewModel, note, navHostController)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            BaseTopAppBar(
                modifier = modifier,
                title = stringResource(Screen.Note.title),
                navigationIcon = Icons.Filled.ArrowBack,
                onNavigationIconClick = {
                    viewModel?.processIntent(
                        NoteViewIntent.SaveNote(mNote)
                    )
                    navHostController.popBackStack()
                },
                actions = {
                    ToolbarActions(note, openDeleteAlertDialog, isFavorite, mNote)
                }
            )
        }, content = { paddingValues ->
            Column(
                modifier
                    .fillMaxSize()
                    .fillMaxWidth()
                    .padding(paddingValues)
                    .imePadding()
                    .background(windowBackgroundColor())
                    .verticalScroll(rememberScrollState())
            ) {
                TitleTextField(
                    modifier,
                    note
                ) {
                    mNote.title = it.title
                    mNote.description = it.description
                }
                DescriptionTextField(
                    modifier,
                    note
                ) {
                    mNote.title = it.title
                    mNote.description = it.description
                }
            }
        })

    BackHandler {
        viewModel?.processIntent(
            NoteViewIntent.SaveNote(mNote)
        )
        navHostController.popBackStack()
    }

}

@Composable
private fun ToolbarActions(
    note: Note,
    openDeleteAlertDialog: MutableState<Boolean>,
    isFavorite: MutableState<Boolean>,
    mNote: Note
) {
    if (!note.isNew) {
        IconButton(onClick = {
            openDeleteAlertDialog.value = true
        }) {
            Icon(
                imageVector = Icons.Filled.Delete,
                contentDescription = null,
                tint = primaryDarkColor()
            )
        }
    }
    if (isFavorite.value) {
        IconButton(onClick = {
            mNote.isFavorite = false
            isFavorite.value = false
        }) {
            Icon(
                imageVector = Icons.Filled.Favorite,
                contentDescription = null,
                tint = primaryDarkColor()
            )
        }
    }
    if (!isFavorite.value) {
        IconButton(onClick = {
            mNote.isFavorite = true
            isFavorite.value = true
        }) {
            Icon(
                imageVector = Icons.Filled.FavoriteBorder,
                contentDescription = null,
                tint = primaryDarkColor()
            )
        }
    }
}

@Composable
private fun ShowDeleteDialog(
    modifier: Modifier,
    openDeleteAlertDialog: MutableState<Boolean>,
    viewModel: NoteViewModel?,
    note: Note,
    navHostController: NavHostController
) {
    BaseAlertDialog(
        modifier = modifier,
        dialogTitle = stringResource(id = R.string.delete_note_dialog_title),
        dialogText = stringResource(id = R.string.delete_note_dialog_text),
        onDismissRequest = {
            openDeleteAlertDialog.value = false

        },
        onConfirmation = {
            openDeleteAlertDialog.value = false
            viewModel?.processIntent(NoteViewIntent.DeleteNote(note))
            navHostController.popBackStack()
        }
    )
}

@Composable
private fun TitleTextField(
    modifier: Modifier,
    note: Note,
    processNote: (Note) -> Unit
) {

    var title by rememberSaveable { mutableStateOf(note.title ?: "") }
    TextField(
        modifier = modifier.fillMaxWidth(),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = windowBackgroundColor(),
            unfocusedContainerColor = windowBackgroundColor(),
            unfocusedPlaceholderColor = softGrayColor(),
            focusedTextColor = primaryDarkColor(),
            unfocusedTextColor = primaryDarkColor(),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
        ),
        textStyle = TextStyle(
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        ),
        placeholder = {
            Text(
                text = stringResource(id = R.string.title),
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        },
        value = title,
        onValueChange = {
            if (it.length <= TITLE_MAX_LENGTH)
                title = it
            note.title = it
            processNote(note)
        },
        maxLines = 1,
        singleLine = true,
    )
}

@Composable
private fun DescriptionTextField(
    modifier: Modifier,
    note: Note,
    processNote: (Note) -> Unit
) {
    var description by rememberSaveable { mutableStateOf(note.description ?: "") }
    TextField(
        modifier = modifier.fillMaxSize(),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = windowBackgroundColor(),
            unfocusedContainerColor = windowBackgroundColor(),
            unfocusedPlaceholderColor = softGrayColor(),
            focusedTextColor = grayColor(),
            unfocusedTextColor = grayColor(),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
        ),
        textStyle = TextStyle(
            fontSize = 20.sp
        ),
        placeholder = {
            Text(
                text = stringResource(id = R.string.description),
                style = TextStyle(
                    fontSize = 20.sp
                )
            )
        },
        value = description,
        onValueChange = {
            if (it.length <= DESCRIPTION_MAX_LENGTH)
                description = it
            note.description = it
            processNote(note)
        }
    )
}

@Preview(
    showSystemUi = true,
    group = DAY_GROUP_PREVIEW,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun NewNoteScreenDayPreview() {
    val navController: NavHostController = rememberNavController()
    NoteScreen(
        navHostController = navController,
        note = Note(
            1,
            "Title",
            "Description"
        ).apply {
            this.isNew = true
        }
    )
}

@Preview(
    showSystemUi = true,
    group = NIGHT_GROUP_PREVIEW,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun NewNoteScreenNightPreview() {
    val navController: NavHostController = rememberNavController()
    NoteScreen(
        navHostController = navController,
        note = Note(
            1,
            "Title",
            "Description"
        ).apply {
            this.isNew = true
        }
    )
}

@Preview(
    showSystemUi = true,
    group = DAY_GROUP_PREVIEW,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun NoteScreenDayPreview() {
    val navController: NavHostController = rememberNavController()
    NoteScreen(
        navHostController = navController,
        note = Note(
            1,
            "Title",
            "Description"
        )
    )
}

@Preview(
    showSystemUi = true,
    group = NIGHT_GROUP_PREVIEW,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun NoteScreenNightPreview() {
    val navController: NavHostController = rememberNavController()
    NoteScreen(
        navHostController = navController,
        note = Note(
            1,
            "Title",
            "Description"
        )
    )
}
