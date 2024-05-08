package com.github.mrzahmadi.lightnote.view.screen.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.mrzahmadi.lightnote.data.DataState
import com.github.mrzahmadi.lightnote.data.model.api.Configs
import com.github.mrzahmadi.lightnote.data.repository.ApiRepository
import com.github.mrzahmadi.lightnote.data.repository.NoteRepository
import com.github.mrzahmadi.lightnote.data.repository.SharedPreferencesRepository
import com.github.mrzahmadi.lightnote.ui.theme.DAY_MODE
import com.google.firebase.crashlytics.FirebaseCrashlytics
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val noteRepository: NoteRepository,
    private val apiRepository: ApiRepository,
    private val sharedPreferencesRepository: SharedPreferencesRepository
) : ViewModel() {

    private var themeChangeListener: ThemeChangeListener? = null

    private val _checkForUpdateState =
        MutableStateFlow<DataState<Configs>>(DataState.Loading(false))
    val checkForUpdateState: StateFlow<DataState<Configs>> = _checkForUpdateState

    private val _getSelectedThemeState =
        MutableStateFlow<DataState<Int>>(DataState.Loading(false))
    val getSelectedThemeState: StateFlow<DataState<Int>> = _getSelectedThemeState

    fun processIntent(intent: ProfileViewIntent) {
        when (intent) {
            is ProfileViewIntent.DeleteNoteList -> removeAllNoteList()
            is ProfileViewIntent.GetConfigs -> getConfigs()
            is ProfileViewIntent.GetSelectedTheme -> getSelectedTheme()
            is ProfileViewIntent.SelectTheme -> {
                saveTheme(intent.theme)
                applyTheme(intent.theme)
            }
        }
    }

    private fun removeAllNoteList() {
        viewModelScope.launch {
            noteRepository.deleteAll()
        }
    }

    private fun getConfigs() {
        viewModelScope.launch {
            try {
                _checkForUpdateState.value = DataState.Loading(true)

                val configs = apiRepository.getConfigs()

                if (configs.isThereNewVersion())
                    _checkForUpdateState.value = DataState.Success(configs)
                else
                    _checkForUpdateState.value = DataState.Empty(true)
                delay(100)
                _checkForUpdateState.value = DataState.Loading(false)
            } catch (e: Exception) {

                FirebaseCrashlytics.getInstance().recordException(e)

                _checkForUpdateState.value = DataState.Loading(false)
                delay(100)
                _checkForUpdateState.value = DataState.Error(e)
            }
        }
    }

    private fun getSelectedTheme() {
        viewModelScope.launch {
            _getSelectedThemeState.value =
                DataState.Success(sharedPreferencesRepository[SharedPreferencesRepository.THEME, DAY_MODE])
            delay(100)
            _getSelectedThemeState.value = DataState.Empty(true)
        }
    }

    private fun saveTheme(theme: Int) {
        viewModelScope.launch {
            sharedPreferencesRepository[SharedPreferencesRepository.THEME] = theme
        }
    }

    private fun applyTheme(theme: Int) {
        viewModelScope.launch {
            delay(10)
            themeChangeListener?.onThemeChanged(theme)
        }
    }

    fun setThemeChangeListener(listener: ThemeChangeListener) {
        themeChangeListener = listener
    }

    interface ThemeChangeListener {
        fun onThemeChanged(theme: Int)
    }

}