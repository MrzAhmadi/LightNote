package com.github.mrzahmadi.lightnote.utils

import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent


object WebIntent {

    fun openLink(
        context: Context,
        url: String
    ) {
        val intent = CustomTabsIntent.Builder()
            .build()
        intent.launchUrl(context, Uri.parse(url))
    }

}