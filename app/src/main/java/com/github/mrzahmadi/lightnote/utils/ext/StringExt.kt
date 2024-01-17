package com.github.mrzahmadi.lightnote.utils.ext

fun String.removeWhitespaces() =
    replace(" ", "")
        .replace("\n","")