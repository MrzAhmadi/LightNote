package com.github.mrzahmadi.lightnote.view.screen.home

import androidx.lifecycle.SavedStateHandle
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
    private val noteRepository: NoteRepository,
    private val savedStateHandle: SavedStateHandle = SavedStateHandle()
) : ViewModel() {

    private val _state = MutableStateFlow<DataState<List<Note>>>(DataState.Loading(true))
    val state: StateFlow<DataState<List<Note>>> = _state


    private fun saveListState(items: List<Note>) {
        savedStateHandle[NOTE_LIST_KEY] = items
    }

    private fun getSavedListState(): List<Note>? {
        savedStateHandle.get<List<Note>>(NOTE_LIST_KEY)?.let { notes ->
            return notes
        }
        return null
    }

    fun processIntent(intent: HomeViewIntent) {
        when (intent) {
            is HomeViewIntent.GetNoteList -> getAll()
            is HomeViewIntent.DeleteNote -> delete(intent.ids)
        }
    }

    private fun getAll() {
        viewModelScope.launch {
            val savedState = getSavedListState()?.let { ArrayList(it) }
            val repoList = ArrayList(noteRepository.getAll())
            if (savedState.isNullOrEmpty()) {
                _state.value = DataState.Loading(true)
                changeState(repoList)
                saveListState(repoList)
            } else {
                if (repoList != savedState) {
                    changeState(repoList)
                    saveListState(repoList)
                }
            }
        }
    }

    private fun delete(noteList: ArrayList<Note>) {
        viewModelScope.launch {
            val ids = noteList.filter {
                it.isSelected
            }.flatMap {
                listOf(it.id)
            }
            noteRepository.delete(ids)
            getAll()
        }
    }

    private fun changeState(notes: ArrayList<Note>) {
        if (notes.isEmpty()) {
            _state.value = DataState.Empty(true)
        } else {
            try {
                _state.value = DataState.Success(notes)
            } catch (e: Exception) {
                _state.value = DataState.Error(e)
            }
        }
    }

    companion object {
        private const val NOTE_LIST_KEY = "NOTE_LIST_STATE"
    }

}