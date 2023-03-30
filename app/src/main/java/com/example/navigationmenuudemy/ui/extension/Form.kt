package com.example.navigationmenuudemy.ui.extension

import com.google.android.material.textfield.TextInputEditText

val TextInputEditText.isEmpty:Boolean
    get() = this.text.toString().trim().isEmpty()