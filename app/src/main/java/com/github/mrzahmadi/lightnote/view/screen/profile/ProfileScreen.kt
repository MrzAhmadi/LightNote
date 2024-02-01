package com.github.mrzahmadi.lightnote.view.screen.profile

import android.content.Context
import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.mrzahmadi.lightnote.R
import com.github.mrzahmadi.lightnote.data.model.Option
import com.github.mrzahmadi.lightnote.ui.theme.dividerColor
import com.github.mrzahmadi.lightnote.ui.theme.windowBackgroundColor
import com.github.mrzahmadi.lightnote.utils.DAY_GROUP_PREVIEW
import com.github.mrzahmadi.lightnote.utils.GITHUB_PROJECT_URL
import com.github.mrzahmadi.lightnote.utils.NIGHT_GROUP_PREVIEW
import com.github.mrzahmadi.lightnote.utils.ext.openLinkByWebIntent
import com.github.mrzahmadi.lightnote.view.Screen
import com.github.mrzahmadi.lightnote.view.widget.BaseAlertDialog
import com.github.mrzahmadi.lightnote.view.widget.BaseTopAppBar
import com.github.mrzahmadi.lightnote.view.widget.ProfileItem

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel? = null
) {

    val openDeleteAlertDialog = remember { mutableStateOf(false) }
    if (openDeleteAlertDialog.value) {
        ShowDeleteDialog(
            modifier,
            openDeleteAlertDialog,
            viewModel
        )
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            BaseTopAppBar(title = stringResource(Screen.Profile.title))
        }, content = { paddingValues ->
            Column(
                modifier
                    .fillMaxSize()
                    .background(windowBackgroundColor())
                    .padding(paddingValues)
            ) {
                val context = LocalContext.current
                ShowList(
                    modifier,
                    Option.getProfileOptionList(context = context)
                ) {
                    onClick(
                        it,
                        openDeleteAlertDialog,
                        context
                    )
                }
            }
        })
}

private fun onClick(
    option: Option,
    openDeleteAlertDialog: MutableState<Boolean>,
    context: Context
) {

    when (option.action) {

        Option.Action.ClearData -> {
            openDeleteAlertDialog.value = true
        }

        Option.Action.Github -> {
            context.openLinkByWebIntent(GITHUB_PROJECT_URL)
        }

        Option.Action.About -> {}
    }

}

@Composable
private fun ShowList(
    modifier: Modifier = Modifier,
    optionList: List<Option>,
    changeSelectedStatus: (Option) -> Unit
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(
            horizontal = 16.dp,
            vertical = 20.dp
        )
    ) {

        itemsIndexed(
            optionList,
        ) { index, item ->
            ProfileItem(
                modifier,
                option = item
            ) {
                changeSelectedStatus(it)
            }
            if (index != optionList.size - 1)
                Divider(
                    modifier.background(
                        dividerColor()
                    )
                )
        }
    }
}

@Composable
private fun ShowDeleteDialog(
    modifier: Modifier,
    openDeleteAlertDialog: MutableState<Boolean>,
    viewModel: ProfileViewModel?
) {
    BaseAlertDialog(
        modifier = modifier,
        dialogTitle = stringResource(id = R.string.clear_data_dialog_title),
        dialogText = stringResource(id = R.string.clear_data_dialog_text),
        onDismissRequest = {
            openDeleteAlertDialog.value = false
        },
        onConfirmation = {
            openDeleteAlertDialog.value = false
            viewModel?.processIntent(ProfileViewIntent.DeleteNoteList)
        }
    )
}

@Preview(
    showSystemUi = true,
    group = DAY_GROUP_PREVIEW,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun ProfileScreenDayPreview() {
    ProfileScreen(
        Modifier
    )
}

@Preview(
    showSystemUi = true,
    group = NIGHT_GROUP_PREVIEW,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun ProfileScreenNightPreview() {
    ProfileScreen(
        Modifier
    )
}