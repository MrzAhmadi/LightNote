package com.github.mrzahmadi.lightnote.view.screen.note

import com.github.mrzahmadi.lightnote.data.model.Note


sealed class NoteViewIntent {
    data class InsertNote(val note: Note) : NoteViewIntent()
    data class UpdateNote(val note: Note) : NoteViewIntent()
}
