package com.github.mrzahmadi.lightnote.view.screen.home

import com.github.mrzahmadi.lightnote.data.model.Note


sealed class HomeViewIntent {
    data object GetNoteList : HomeViewIntent()
    data class DeleteNote(val note: Note) : HomeViewIntent()
}
