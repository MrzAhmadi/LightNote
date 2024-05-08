package com.github.mrzahmadi.lightnote.view.screen.profile

import android.content.Context
import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.github.mrzahmadi.lightnote.R
import com.github.mrzahmadi.lightnote.data.DataState
import com.github.mrzahmadi.lightnote.data.model.Option
import com.github.mrzahmadi.lightnote.data.model.api.Configs
import com.github.mrzahmadi.lightnote.ui.theme.dividerColor
import com.github.mrzahmadi.lightnote.ui.theme.windowBackgroundColor
import com.github.mrzahmadi.lightnote.utils.DAY_GROUP_PREVIEW
import com.github.mrzahmadi.lightnote.utils.GITHUB_PROJECT_RELEASE_URL
import com.github.mrzahmadi.lightnote.utils.GITHUB_PROJECT_URL
import com.github.mrzahmadi.lightnote.utils.NIGHT_GROUP_PREVIEW
import com.github.mrzahmadi.lightnote.utils.ext.openLinkByWebIntent
import com.github.mrzahmadi.lightnote.utils.ext.showToast
import com.github.mrzahmadi.lightnote.view.Screen
import com.github.mrzahmadi.lightnote.view.widget.BaseAlertDialog
import com.github.mrzahmadi.lightnote.view.widget.BaseTopAppBar
import com.github.mrzahmadi.lightnote.view.widget.OptionListAlertDialog
import com.github.mrzahmadi.lightnote.view.widget.ProfileItem

var selectedTheme = 0

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel? = null
) {

    val checkForUpdateState: State<DataState<Configs>>? =
        viewModel?.checkForUpdateState?.collectAsState()

    val getSelectedThemeState: State<DataState<Int>>? =
        viewModel?.getSelectedThemeState?.collectAsState()

    when (getSelectedThemeState?.value) {

        is DataState.Success -> {
            selectedTheme = (getSelectedThemeState.value as DataState.Success<Int>).data
        }

        else -> {}
    }

    viewModel?.processIntent(ProfileViewIntent.GetSelectedTheme)

    val showSelectThemeDialog = remember { mutableStateOf(false) }
    if (showSelectThemeDialog.value) {
        ShowSelectThemeDialog(
            modifier,
            showSelectThemeDialog,
            viewModel
        )
    }


    val showUpdateDialog = remember { mutableStateOf(false) }
    if (showUpdateDialog.value) {
        ShowUpdateDialog(
            modifier,
            showUpdateDialog,
            context = LocalContext.current
        )
    }

    val showDeleteAlertDialog = remember { mutableStateOf(false) }
    if (showDeleteAlertDialog.value) {
        ShowDeleteDialog(
            modifier,
            showDeleteAlertDialog,
            viewModel
        )
    }

    val showLoadingDialog = remember { mutableStateOf(false) }
    if (showLoadingDialog.value) {
        ShowLoadingDialog(
            modifier,
            showLoadingDialog
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
                    Option.getProfileOptionList(
                        context = context,
                        selectedTheme = getThemeTitle(context)
                    )
                ) {
                    onClick(
                        it,
                        showDeleteAlertDialog,
                        showSelectThemeDialog,
                        context,
                        viewModel,
                    )
                }
            }
        })

    when (checkForUpdateState?.value) {
        is DataState.Loading -> {
            val state = checkForUpdateState.value as DataState.Loading<Configs>
            showLoadingDialog.value = state.isLoading
        }

        is DataState.Error -> {
            val state = checkForUpdateState.value as DataState.Error<Configs>
            state.error.localizedMessage?.let {
                LocalContext.current.showToast(
                    it,
                    Toast.LENGTH_LONG
                )
            }
        }

        is DataState.Empty -> {
            LocalContext.current.showToast(
                stringResource(id = R.string.using_latest_version),
                Toast.LENGTH_LONG
            )
        }

        is DataState.Success -> {
            showUpdateDialog.value = true
        }

        null -> {}
    }

}

@Composable
private fun getThemeTitle(context: Context) = when (selectedTheme) {
    0 -> context.getString(R.string.item_option_description_theme_light)
    1 -> context.getString(
        R.string.item_option_description_theme_dark
    )
    else -> context.getString(R.string.item_option_description_theme_system)
}

private fun onClick(
    option: Option,
    openDeleteAlertDialog: MutableState<Boolean>,
    showSelectThemeDialog: MutableState<Boolean>,
    context: Context,
    viewModel: ProfileViewModel?
) {

    when (option.action) {

        Option.Action.SELECT_THEME -> {
            showSelectThemeDialog.value = true
        }

        Option.Action.CLEAR_DATA -> {
            openDeleteAlertDialog.value = true
        }

        Option.Action.GITHUB -> {
            context.openLinkByWebIntent(GITHUB_PROJECT_URL)
        }

        Option.Action.CHECK_FOR_UPDATE -> {
            viewModel?.processIntent(ProfileViewIntent.GetConfigs)
        }

        Option.Action.ABOUT -> {}
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
                HorizontalDivider(
                    modifier.background(
                        dividerColor()
                    )
                )
        }
    }
}

@Composable
private fun ShowLoadingDialog(
    modifier: Modifier,
    show: MutableState<Boolean>
) {
    Dialog(
        onDismissRequest = { show.value = false },
        DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
                .size(100.dp)
                .background(windowBackgroundColor(), shape = RoundedCornerShape(8.dp))
        ) {
            CircularProgressIndicator()
        }
    }
}

@Composable
private fun ShowUpdateDialog(
    modifier: Modifier,
    show: MutableState<Boolean>,
    context: Context,
) {
    BaseAlertDialog(
        modifier = modifier,
        title = stringResource(id = R.string.new_version_is_available_dialog_title),
        text = stringResource(id = R.string.new_version_is_available_dialog_text),
        confirmButtonText = stringResource(id = R.string.new_version_is_available_dialog_positive),
        onDismissRequest = {
            show.value = false
        },
        onConfirmation = {
            show.value = false
            context.openLinkByWebIntent(GITHUB_PROJECT_RELEASE_URL)
        }
    )
}

@Composable
private fun ShowDeleteDialog(
    modifier: Modifier,
    show: MutableState<Boolean>,
    viewModel: ProfileViewModel?
) {
    BaseAlertDialog(
        modifier = modifier,
        title = stringResource(id = R.string.clear_data_dialog_title),
        text = stringResource(id = R.string.clear_data_dialog_text),
        onDismissRequest = {
            show.value = false
        },
        onConfirmation = {
            show.value = false
            viewModel?.processIntent(ProfileViewIntent.DeleteNoteList)
        }
    )
}

@Composable
private fun ShowSelectThemeDialog(
    modifier: Modifier,
    show: MutableState<Boolean>,
    viewModel: ProfileViewModel?
) {
    OptionListAlertDialog(
        modifier = modifier,
        title = stringResource(id = R.string.item_option_title_night_mode),
        onDismissRequest = {
            show.value = false
        },
        optionsList = arrayListOf(
            stringResource(id = R.string.item_option_description_theme_light),
            stringResource(id = R.string.item_option_description_theme_dark),
            stringResource(id = R.string.item_option_description_theme_system),
        ),
        defaultSelected = selectedTheme,
        onSubmitButtonClick = {
            viewModel?.processIntent(ProfileViewIntent.SelectTheme(it))
            show.value = false
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