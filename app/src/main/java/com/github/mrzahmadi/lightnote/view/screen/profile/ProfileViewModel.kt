package com.github.mrzahmadi.lightnote.view.screen.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.mrzahmadi.lightnote.data.DataState
import com.github.mrzahmadi.lightnote.data.api.ApiService
import com.github.mrzahmadi.lightnote.data.model.api.Configs
import com.github.mrzahmadi.lightnote.data.repository.NoteRepository
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
    private val apiService: ApiService
) : ViewModel() {

    private val _checkForUpdateState =
        MutableStateFlow<DataState<Configs>>(DataState.Loading(false))
    val checkForUpdateState: StateFlow<DataState<Configs>> = _checkForUpdateState

    fun processIntent(intent: ProfileViewIntent) {
        when (intent) {
            is ProfileViewIntent.DeleteNoteList -> removeAllNoteList()
            is ProfileViewIntent.FetchConfigs -> fetchConfigs()
        }
    }

    private fun removeAllNoteList() {
        viewModelScope.launch {
            noteRepository.deleteAll()
        }
    }

    private fun fetchConfigs() {
        viewModelScope.launch {
            try {
                _checkForUpdateState.value = DataState.Loading(true)

                val configs = apiService.getConfigs()

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
}