package com.example.navigationmenuudemy.ui.extension

import android.util.Log

fun Any?.printToLog(tag: String = "DEBUG_LOG") {
    Log.d(tag, toString())
}

val Any?.isNull get() = this == null