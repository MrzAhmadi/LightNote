package com.github.mrzahmadi.lightnote.view.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.github.mrzahmadi.lightnote.R
import com.github.mrzahmadi.lightnote.data.model.Note
import com.github.mrzahmadi.lightnote.ui.theme.grayColor
import com.github.mrzahmadi.lightnote.ui.theme.primaryDarkColor
import com.github.mrzahmadi.lightnote.ui.theme.softGrayColor
import com.github.mrzahmadi.lightnote.ui.theme.windowBackgroundColor
import com.github.mrzahmadi.lightnote.view.Screen
import com.github.mrzahmadi.lightnote.view.widget.BaseTopAppBarWithBack

@Composable
fun NoteScreen(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    note: Note?
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            BaseTopAppBarWithBack(
                title = stringResource(Screen.Note.title),
                onClick = {
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
                )
                DescriptionTextField(
                    modifier,
                    note
                )
            }
        })
}

@Composable
fun KeyboardAware(
    content: @Composable () -> Unit
) {
    Box(modifier = Modifier.imePadding()) {
        content()
    }
}

@Composable
private fun TitleTextField(
    modifier: Modifier,
    note: Note?
) {

    var title by rememberSaveable { mutableStateOf(note?.title ?: "") }
    val maxLength = 50
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
            if (it.length <= maxLength)
                title = it
        },
        maxLines = 1,
        singleLine = true,
    )
}

@Composable
private fun DescriptionTextField(
    modifier: Modifier,
    note: Note?
) {
    val maxLength = 10000
    var description by rememberSaveable { mutableStateOf(note?.description ?: "") }
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
            if (it.length <= maxLength)
                description = it
        }
    )
}


@Preview(showSystemUi = true)
@Composable
fun NoteScreenPreview() {
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