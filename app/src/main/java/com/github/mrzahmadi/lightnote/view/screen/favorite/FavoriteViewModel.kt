package com.github.mrzahmadi.lightnote.view.screen.favorite

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
class FavoriteViewModel @Inject constructor(
    private val noteRepository: NoteRepository,
    private val savedStateHandle: SavedStateHandle = SavedStateHandle()
) : ViewModel() {

    private val _state = MutableStateFlow<DataState<List<Note>>>(DataState.Loading(true))
    val state: StateFlow<DataState<List<Note>>> = _state

    private fun saveListState(items: List<Note>) {
        savedStateHandle[FAVORITE_NOTE_LIST_KEY] = items
    }

    private fun getSavedListState(): List<Note>? {
        savedStateHandle.get<List<Note>>(FAVORITE_NOTE_LIST_KEY)?.let { notes ->
            return notes
        }
        return null
    }

    fun processIntent(intent: FavoriteViewIntent) {
        when (intent) {
            is FavoriteViewIntent.GetFavoriteNoteList -> getFavoriteNoteList()
            is FavoriteViewIntent.DeleteNote -> delete(intent.note)
        }
    }

    private fun getFavoriteNoteList() {
        viewModelScope.launch {
            val savedState = getSavedListState()?.let { ArrayList(it) }
            val repoList = ArrayList(noteRepository.getFavoriteList())
            if (savedState.isNullOrEmpty()) {
                _state.value = DataState.Loading(true)
                changeState(repoList)
                saveListState(repoList)
            } else {
                if (repoList != savedState) {
                    changeState(repoList)
                    saveListState(repoList)
                } else
                    changeState(savedState)
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
            getFavoriteNoteList()
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
        private const val FAVORITE_NOTE_LIST_KEY = "FAVORITE_NOTE_LIST_STATE"
    }

}