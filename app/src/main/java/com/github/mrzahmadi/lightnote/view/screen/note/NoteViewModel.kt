package com.github.mrzahmadi.lightnote.view.screen.note

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.savedstate.SavedStateRegistryOwner
import com.github.mrzahmadi.lightnote.data.model.Note
import com.github.mrzahmadi.lightnote.data.repository.NoteRepository
import com.github.mrzahmadi.lightnote.utils.ext.removeWhitespaces
import kotlinx.coroutines.launch

class NoteViewModel(
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
                note.title?.removeWhitespaces().isNullOrEmpty() &&
                note.description?.removeWhitespaces().isNullOrEmpty()
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
                    return NoteViewModel(noteRepository) as T
                }
            }
    }

}
