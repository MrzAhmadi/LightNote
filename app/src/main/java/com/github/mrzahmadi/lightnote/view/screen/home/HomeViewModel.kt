package com.github.mrzahmadi.lightnote.view.screen.home

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.savedstate.SavedStateRegistryOwner
import com.github.mrzahmadi.lightnote.data.DataState
import com.github.mrzahmadi.lightnote.data.model.Note
import com.github.mrzahmadi.lightnote.data.repository.NoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val noteRepository: NoteRepository
) : ViewModel() {

    private val _state = MutableStateFlow<DataState<List<Note>>>(DataState.Loading(true))
    val state: StateFlow<DataState<List<Note>>> = _state

    fun processIntent(intent: HomeViewIntent) {
        when (intent) {
            is HomeViewIntent.FetchNoteList -> getAll()
        }
    }

    private fun getAll() {
        viewModelScope.launch {
            viewModelScope.launch {
                _state.value = DataState.Loading(true)
                try {
                    val notes = ArrayList(noteRepository.getAll())
                    _state.value = DataState.Success(notes)
                } catch (e: Exception) {
                    _state.value = DataState.Error(e)
                }
            }
        }
    }

    companion object {

        fun provideFactory(
            noteRepository: NoteRepository,
            owner: SavedStateRegistryOwner,
            defaultArgs: Bundle? = null,
        ): AbstractSavedStateViewModelFactory =
            object : AbstractSavedStateViewModelFactory(
                owner,
                defaultArgs
            ) {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(
                    key: String,
                    modelClass: Class<T>,
                    handle: SavedStateHandle
                ): T {
                    return HomeViewModel(noteRepository) as T
                }
            }
    }

}
