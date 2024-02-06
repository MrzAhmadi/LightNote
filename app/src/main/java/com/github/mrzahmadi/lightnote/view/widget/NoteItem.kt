package com.github.mrzahmadi.lightnote.view.widget

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.github.mrzahmadi.lightnote.ui.theme.lightGrayColor
import com.github.mrzahmadi.lightnote.ui.theme.primaryDarkColor
import com.github.mrzahmadi.lightnote.view.Screen
import com.google.gson.Gson

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteItem(
    modifier: Modifier = Modifier,
    note: Note,
    navHostController: NavHostController? = null,
    changeSelected: ((Note) -> Unit)? = null,
    checkOtherItemsSelected: (() -> Boolean)? = null,
    isSelected: MutableState<Boolean>
) {

    Card(
        modifier = modifier
            .padding(
                top = 8.dp,
                bottom = 8.dp
            )
            .fillMaxWidth(),
        shape = RoundedCornerShape(
            size = 4.dp,
        ), colors = CardDefaults.cardColors(
            if (!isSelected.value)
                cardBackgroundColor()
            else
                lightGrayColor()
        ), elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
    ) {
        Column(
            modifier = modifier
                .combinedClickable(
                    onClick = {
                        if (isSelected.value) {
                            isSelected.value = false
                            note.isSelectedState.value = false
                            changeSelected?.let { it(note) }
                        } else {
                            val otherItemsSelected =
                                checkOtherItemsSelected?.invoke() ?: false
                            if (otherItemsSelected) {
                                isSelected.value = true
                                note.isSelectedState.value = true
                                changeSelected?.let { it(note) }
                            } else
                                navHostController?.navigate(
                                    "${Screen.Note.route}?${Gson().toJson(note)}"
                                )
                        }
                    },
                    onLongClick = {
                        isSelected.value = !isSelected.value
                        note.isSelectedState.value = isSelected.value
                        changeSelected?.let { it(note) }
                    }
                )
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
                    ),
                    maxLines = 2
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
        ),
        isSelected = remember {
            mutableStateOf(false)
        }
    )
}