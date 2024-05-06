package com.github.mrzahmadi.lightnote.view.widget

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.github.mrzahmadi.lightnote.R
import com.github.mrzahmadi.lightnote.ui.theme.cardBackgroundColor
import com.github.mrzahmadi.lightnote.ui.theme.grayColor
import com.github.mrzahmadi.lightnote.ui.theme.primaryDarkColor
import com.github.mrzahmadi.lightnote.utils.DAY_GROUP_PREVIEW
import com.github.mrzahmadi.lightnote.utils.NIGHT_GROUP_PREVIEW

@Composable
fun BaseAlertDialog(
    modifier: Modifier = Modifier,
    title: String,
    text: String,
    confirmButtonText: String = stringResource(id = R.string.confirm),
    dismissButtonText: String = stringResource(id = R.string.dismiss),
    icon: ImageVector? = null,
    onDismissRequest: (() -> Unit)? = null,
    onConfirmation: (() -> Unit)? = null,
) {
    AlertDialog(
        modifier = modifier
            .fillMaxWidth()
            .defaultMinSize(
                minHeight = 180.dp
            ),
        icon = {
            if (icon != null) {
                Icon(
                    icon,
                    contentDescription = null,
                    tint = primaryDarkColor()
                )
            }
        },
        title = {
            Text(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(
                        start = 16.dp,
                        end = 16.dp
                    ),
                text = title,
                color = primaryDarkColor(),
                style = TextStyle(
                    textAlign = TextAlign.Center
                ),
                fontSize = 16.sp,
            )
        },
        text = {
            Text(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(
                        start = 16.dp,
                        end = 16.dp
                    ),
                text = text,
                color = primaryDarkColor(),
                style = TextStyle(
                    textAlign = TextAlign.Start
                ),
                fontSize = 12.sp,
            )
        },
        onDismissRequest = {
            onDismissRequest?.invoke()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation?.invoke()
                },
            ) {
                Text(
                    confirmButtonText,
                    color = primaryDarkColor(),
                    fontSize = 12.sp
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest?.invoke()
                }
            ) {
                Text(
                    dismissButtonText,
                    color = primaryDarkColor(),
                    fontSize = 12.sp
                )
            }
        },
        containerColor = cardBackgroundColor()
    )
}

@Preview(
    group = DAY_GROUP_PREVIEW,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun BaseAlertDialogDayPreview() {
    BaseAlertDialog(
        title = "Title",
        text = "Text",
        icon = Icons.Filled.Delete,
    )
}

@Preview(
    group = NIGHT_GROUP_PREVIEW,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun BaseAlertDialogNightPreview() {
    BaseAlertDialog(
        title = "Title",
        text = "Text",
        icon = Icons.Filled.Delete,
    )
}


@Composable
fun OptionListAlertDialog(
    modifier: Modifier = Modifier,
    title: String,
    optionsList: List<String>,
    defaultSelected: Int,
    onDismissRequest: (() -> Unit)? = null,
    onSubmitButtonClick: ((Int) -> Unit)? = null,
) {
    Dialog(
        onDismissRequest = { onDismissRequest?.invoke() }
    ) {
        Surface(
            modifier = modifier,
            shape = RoundedCornerShape(8.dp)
        ) {
            Column(
                modifier = modifier
                    .background(cardBackgroundColor()),
                verticalArrangement = Arrangement.Center
            ) {

                Spacer(modifier = modifier.height(16.dp))

                Text(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(
                            start = 16.dp,
                            end = 16.dp
                        ),
                    fontSize = 16.sp,
                    text = title,
                    color = primaryDarkColor(),
                    style = TextStyle(
                        textAlign = TextAlign.Center
                    ),
                )

                Spacer(modifier = modifier.height(8.dp))

                LazyColumn(modifier = modifier) {
                    itemsIndexed(optionsList) { index, item ->
                        Row(
                            modifier = modifier
                                .fillMaxWidth()
                                .clickable {
                                    onSubmitButtonClick?.invoke(index)
                                },
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {

                            Spacer(modifier = modifier.width(16.dp))

                            RadioButton(
                                selected = index == defaultSelected,
                                onClick = null
                            )

                            Text(
                                modifier = modifier.padding(
                                    start = 8.dp,
                                    top = 16.dp,
                                    bottom = 16.dp
                                ),
                                text = item,
                                color = grayColor(),
                                style = TextStyle(
                                    textAlign = TextAlign.Center
                                ),
                                fontSize = 12.sp,
                            )

                            Spacer(modifier = modifier.width(16.dp))

                        }
                    }
                }

                Spacer(modifier = modifier.height(16.dp))

            }
        }
    }
}

@Preview(
    group = DAY_GROUP_PREVIEW,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun OptionListAlertDialogPreview() {
    OptionListAlertDialog(
        title = "Title",
        optionsList = arrayListOf("Item 1", "Item 2"),
        defaultSelected = 0
    )
}

@Preview(
    group = NIGHT_GROUP_PREVIEW,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun OptionListAlertDialogNightPreview() {
    OptionListAlertDialog(
        title = "Title",
        optionsList = arrayListOf("Item 1", "Item 2"),
        defaultSelected = 0
    )
}


