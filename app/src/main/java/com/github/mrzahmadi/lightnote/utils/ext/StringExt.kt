package com.github.mrzahmadi.lightnote.utils.ext

fun String.removeWhitespaces() =
    replace(" ", "")

fun String.removeEmptyLines() =
    lines().joinToString("")
