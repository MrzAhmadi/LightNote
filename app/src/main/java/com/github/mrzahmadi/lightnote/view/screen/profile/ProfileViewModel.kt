package com.github.mrzahmadi.lightnote.view.screen.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.mrzahmadi.lightnote.data.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val noteRepository: NoteRepository
) : ViewModel() {

    fun processIntent(intent: ProfileViewIntent) {
        when (intent) {
            is ProfileViewIntent.DeleteNoteList -> removeAllNoteList()
        }
    }

    private fun removeAllNoteList() {
        viewModelScope.launch {
            noteRepository.deleteAll()
        }
    }

}