package com.github.mrzahmadi.lightnote.view.screen.profile


sealed class ProfileViewIntent {
    data object DeleteNoteList : ProfileViewIntent()
}
