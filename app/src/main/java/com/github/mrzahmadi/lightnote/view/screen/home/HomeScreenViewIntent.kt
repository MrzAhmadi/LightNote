package com.github.mrzahmadi.lightnote.view.screen.home

sealed class HomeScreenViewIntent {
    data object FetchNotes : HomeScreenViewIntent()
}
