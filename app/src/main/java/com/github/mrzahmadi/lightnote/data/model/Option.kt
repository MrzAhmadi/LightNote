package com.github.mrzahmadi.lightnote.data.model

import androidx.compose.ui.graphics.vector.ImageVector

data class Option(
    val action: Action,
    val icon: ImageVector,
    val title: String,
    val description: String
) {

    enum class Action {
        ClearData,
        Github,
        About
    }

}
