package com.github.mrzahmadi.lightnote.view.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.mrzahmadi.lightnote.data.DataState
import com.github.mrzahmadi.lightnote.data.model.Note
import com.github.mrzahmadi.lightnote.data.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
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

}
