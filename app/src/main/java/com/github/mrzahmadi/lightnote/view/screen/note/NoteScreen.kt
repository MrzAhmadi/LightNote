package com.github.mrzahmadi.lightnote.view.screen.note

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalSavedStateRegistryOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.github.mrzahmadi.lightnote.R
import com.github.mrzahmadi.lightnote.data.db.DatabaseBuilder
import com.github.mrzahmadi.lightnote.data.model.Note
import com.github.mrzahmadi.lightnote.data.repository.NoteRepository
import com.github.mrzahmadi.lightnote.ui.theme.grayColor
import com.github.mrzahmadi.lightnote.ui.theme.primaryDarkColor
import com.github.mrzahmadi.lightnote.ui.theme.softGrayColor
import com.github.mrzahmadi.lightnote.ui.theme.windowBackgroundColor
import com.github.mrzahmadi.lightnote.view.Screen
import com.github.mrzahmadi.lightnote.view.widget.BaseTopAppBarWithBack


private const val TITLE_MAX_LENGTH = 50
private const val DESCRIPTION_MAX_LENGTH = 10000

@Composable
fun NoteScreen(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    note: Note,
    noteViewModel: NoteViewModel,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
) {

    val mNote by remember { derivedStateOf { note } }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            BaseTopAppBarWithBack(
                title = stringResource(Screen.Note.title),
                onClick = {
                    noteViewModel.processIntent(
                        NoteViewIntent.SaveNote(mNote)
                    )
                    navHostController.popBackStack()
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
        noteViewModel.processIntent(
            NoteViewIntent.SaveNote(mNote)
        )
        navHostController.popBackStack()
    }

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


@Preview(showSystemUi = true)
@Composable
fun NoteScreenPreview() {
    val navController: NavHostController = rememberNavController()
    val dao = DatabaseBuilder.getInstance(LocalContext.current).noteDao()
    val noteRepository = NoteRepository(dao)
    NoteScreen(
        navHostController = navController,
        note = Note(
            1,
            "Title",
            "Description"
        ),
        noteViewModel = viewModel(
            factory = NoteViewModel.provideFactory(
                noteRepository = noteRepository,
                owner = LocalSavedStateRegistryOwner.current
            )
        )

    )
}