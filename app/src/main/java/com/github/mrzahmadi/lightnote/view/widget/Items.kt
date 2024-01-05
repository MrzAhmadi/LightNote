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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.mrzahmadi.lightnote.data.model.Note
import com.github.mrzahmadi.lightnote.ui.theme.cardBackgroundColor
import com.github.mrzahmadi.lightnote.ui.theme.grayColor
import com.github.mrzahmadi.lightnote.ui.theme.primaryDarkColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteItem(
    modifier: Modifier = Modifier,
    note: Note
) {
    Card(
        onClick = {},
        modifier = modifier
            .padding(
                top = 8.dp,
                bottom = 8.dp
            )
            .fillMaxWidth(),
        shape = RoundedCornerShape(
            size = 4.dp,
        ),
        colors = CardDefaults.cardColors(
            cardBackgroundColor()
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
    ) {
        Column(
            modifier = modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = note.title,
                textAlign = TextAlign.Start,
                fontSize = 16.sp,
                color = primaryDarkColor()
            )
            Spacer(modifier = modifier.padding(4.dp))
            Text(
                text = note.description,
                textAlign = TextAlign.Start,
                fontSize = 12.sp,
                color = grayColor()
            )
        }
    }
}

@Preview
@Composable
fun MyViewPreview() {
    NoteItem(
        note = Note(
            "Title",
            "Description"
        )
    )
}