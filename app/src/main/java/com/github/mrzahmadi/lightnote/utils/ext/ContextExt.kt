package com.github.mrzahmadi.lightnote.utils.ext

import android.content.Context
import android.content.ContextWrapper
import android.widget.Toast
import androidx.activity.ComponentActivity
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

fun Context.getLifeCycleOwner() : ComponentActivity? = when {
    this is ContextWrapper -> if (this is ComponentActivity) this else this.baseContext.getLifeCycleOwner()
    else -> null
}