package com.github.mrzahmadi.lightnote.utils.ext

import android.content.Context
import android.widget.Toast
import com.github.mrzahmadi.lightnote.utils.WebIntent

fun Context.showToast(
    message: String,
    length: Int = Toast.LENGTH_SHORT
) {
    Toast.makeText(
        this,
        message,
        length
    ).show()
}

fun Context.openLinkByWebIntent(url: String) {
    WebIntent.openLink(
        this,
        url
    )
}
