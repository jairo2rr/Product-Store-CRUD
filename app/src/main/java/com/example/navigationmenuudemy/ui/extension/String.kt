package com.example.navigationmenuudemy.ui.extension

val String.upperFirstChar:String
    get() = this.lowercase().replaceFirstChar { first -> first.uppercase() }