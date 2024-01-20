package com.github.mrzahmadi.lightnote.view.screen.note

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.mrzahmadi.lightnote.data.model.Note
import com.github.mrzahmadi.lightnote.data.repository.NoteRepository
import com.github.mrzahmadi.lightnote.utils.ext.removeEmptyLines
import com.github.mrzahmadi.lightnote.utils.ext.removeWhitespaces
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val noteRepository: NoteRepository
) : ViewModel() {

    fun processIntent(intent: NoteViewIntent) {
        when (intent) {
            is NoteViewIntent.SaveNote -> checkInsertOrUpdate(intent.note)
        }
    }

    private fun checkInsertOrUpdate(note: Note) {
        if (note.isNew) {
            if (
                note.title?.removeEmptyLines()?.removeWhitespaces().isNullOrEmpty() &&
                note.description?.removeEmptyLines()?.removeWhitespaces().isNullOrEmpty()
            )
                return
            insert(note)
        } else
            update(note)
    }

    private fun insert(note: Note) {
        viewModelScope.launch {
            noteRepository.insert(note)
        }
    }

    private fun update(note: Note) {
        viewModelScope.launch {
            noteRepository.update(note)
        }
    }

}
