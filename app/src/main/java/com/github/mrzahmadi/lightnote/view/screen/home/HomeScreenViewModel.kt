package com.github.mrzahmadi.lightnote.view.screen.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.mrzahmadi.lightnote.data.DataState
import com.github.mrzahmadi.lightnote.data.model.Note
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeScreenViewModel : ViewModel() {
    private val _state = MutableStateFlow<DataState<List<Note>>>(DataState.Loading(true))
    val state: StateFlow<DataState<List<Note>>> = _state

    private val savedStateHandle = SavedStateHandle()

    private fun saveNoteList(noteList: ArrayList<Note>) {
        savedStateHandle["NoteList"] = Gson().toJson(noteList)
    }

    private fun restoreNoteList(): List<Note>? {
        val noteListJson = savedStateHandle.get<String>("NoteList")
        if (!noteListJson.isNullOrBlank()) {
            val type = object : TypeToken<List<Note>>() {}.type
            return Gson().fromJson(noteListJson, type)
        }
        return null
    }

    fun processIntent(intent: HomeScreenViewIntent) {
        when (intent) {
            is HomeScreenViewIntent.FetchNotes -> fetchNotes()
        }
    }

    private fun fetchNotes() {
        val restoreData = restoreNoteList()
        if (restoreData != null) {
            _state.value = DataState.Success(restoreData)
        } else {
            viewModelScope.launch {
                _state.value = DataState.Loading(true)
                delay(2000)
                try {
                    val notes = Note.getTestingList()
                    saveNoteList(notes)
                    _state.value = DataState.Success(notes)
                } catch (e: Exception) {
                    _state.value = DataState.Error(e)
                }
            }
        }
    }
}