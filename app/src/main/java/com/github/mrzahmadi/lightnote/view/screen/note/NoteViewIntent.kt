package com.github.mrzahmadi.lightnote.view.screen.note

import com.github.mrzahmadi.lightnote.data.model.Note


sealed class NoteViewIntent {
    data class SaveNote(val note: Note) : NoteViewIntent()
    data class DeleteNote(val note: Note) : NoteViewIntent()
}
