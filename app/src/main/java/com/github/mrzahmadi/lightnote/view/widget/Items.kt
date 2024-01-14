package com.github.mrzahmadi.lightnote.view.widget

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.github.mrzahmadi.lightnote.data.model.Note
import com.github.mrzahmadi.lightnote.ui.theme.cardBackgroundColor
import com.github.mrzahmadi.lightnote.ui.theme.grayColor
import com.github.mrzahmadi.lightnote.ui.theme.primaryDarkColor
import com.github.mrzahmadi.lightnote.view.Screen
import com.google.gson.Gson

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteItem(
    modifier: Modifier = Modifier,
    note: Note,
    navHostController: NavHostController? = null,
) {
    Card(
        onClick = {
            navHostController?.navigate(
                "${Screen.Note.route}?${Gson().toJson(note)}"
            )
        }, modifier = modifier
            .padding(
                top = 8.dp,
                bottom = 8.dp
            )
            .fillMaxWidth(), shape = RoundedCornerShape(
            size = 4.dp,
        ), colors = CardDefaults.cardColors(
            cardBackgroundColor()
        ), elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
    ) {
        Column(
            modifier = modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            note.title?.let {
                Text(
                    text = it,
                    textAlign = TextAlign.Start,
                    color = primaryDarkColor(),
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
            note.description?.let {
                if (note.title != null)
                    Spacer(modifier = modifier.padding(4.dp))
                Text(
                    text = it,
                    textAlign = TextAlign.Start,
                    color = grayColor(),
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    ),
                    maxLines = 3
                )
            }
        }
    }
}

@Preview
@Composable
fun MyViewPreview() {
    NoteItem(
        note = Note(
            1,
            "Title",
            "Description"
        )
    )
}