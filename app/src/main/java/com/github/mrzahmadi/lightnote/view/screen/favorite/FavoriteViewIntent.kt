package com.github.mrzahmadi.lightnote.view.screen.favorite

import com.github.mrzahmadi.lightnote.data.model.Note


sealed class FavoriteViewIntent {
    data object GetFavoriteNoteList : FavoriteViewIntent()
    data class DeleteNote(val note: ArrayList<Note>) : FavoriteViewIntent()
}
