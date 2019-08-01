package com.airmap.airmapsdk

import android.content.SharedPreferences

private const val LOGGED_IN_KEY = "logged_in"

var SharedPreferences.isLoggedIn: Boolean
    get() = getBoolean(LOGGED_IN_KEY, false)
    set(value) = edit().putBoolean(LOGGED_IN_KEY, value).apply()