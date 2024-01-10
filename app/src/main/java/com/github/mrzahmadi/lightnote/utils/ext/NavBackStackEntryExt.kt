package com.github.mrzahmadi.lightnote.utils.ext

import androidx.navigation.NavDestination

fun NavDestination.getRouteWithoutParams(): String? {
    return route?.split("?")?.get(0)
}