package com.github.mrzahmadi.lightnote.view.widget

import android.content.res.Configuration
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.github.mrzahmadi.lightnote.R
import com.github.mrzahmadi.lightnote.ui.theme.cardBackgroundColor
import com.github.mrzahmadi.lightnote.ui.theme.primaryDarkColor
import com.github.mrzahmadi.lightnote.utils.DAY_GROUP_PREVIEW
import com.github.mrzahmadi.lightnote.utils.NIGHT_GROUP_PREVIEW

@Composable
fun BaseAlertDialog(
    modifier: Modifier = Modifier,
    dialogTitle: String,
    dialogText: String,
    confirmButtonText: String = stringResource(id = R.string.confirm),
    dismissButtonText: String = stringResource(id = R.string.dismiss),
    icon: ImageVector? = null,
    onDismissRequest: (() -> Unit)? = null,
    onConfirmation: (() -> Unit)? = null,
) {
    AlertDialog(
        modifier = modifier,
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
                text = dialogTitle,
                color = primaryDarkColor(),
                style = TextStyle(
                    textAlign = TextAlign.Center
                )
            )
        },
        text = {
            Text(
                text = dialogText,
                color = primaryDarkColor(),
                style = TextStyle(
                    textAlign = TextAlign.Center
                )
            )
        },
        onDismissRequest = {
            onDismissRequest?.let { it() }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation?.let { it() }
                }
            ) {
                Text(
                    confirmButtonText,
                    color = primaryDarkColor()
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest?.let { it() }
                }
            ) {
                Text(
                    dismissButtonText,
                    color = primaryDarkColor()
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
        dialogTitle = "Title",
        dialogText = "Text",
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
        dialogTitle = "Title",
        dialogText = "Text",
        icon = Icons.Filled.Delete,
    )
}