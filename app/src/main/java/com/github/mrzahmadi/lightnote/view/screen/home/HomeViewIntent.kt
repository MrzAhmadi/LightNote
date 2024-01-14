package com.github.mrzahmadi.lightnote.view.screen.home


sealed class HomeViewIntent {
    data object FetchNoteList : HomeViewIntent()
}
