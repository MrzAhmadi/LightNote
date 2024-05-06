package com.github.mrzahmadi.lightnote.view.screen.profile


sealed class ProfileViewIntent {
    data object DeleteNoteList : ProfileViewIntent()
    data object GetConfigs : ProfileViewIntent()
    data object GetSelectedTheme : ProfileViewIntent()
    data class SelectTheme(val theme: Int) : ProfileViewIntent()
}
