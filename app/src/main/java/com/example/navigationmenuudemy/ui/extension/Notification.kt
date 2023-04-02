package com.example.navigationmenuudemy.ui.extension

import android.app.Activity
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

fun Fragment.toast(message:String){
    Toast.makeText(requireContext(),message, Toast.LENGTH_SHORT).show()
}

fun Fragment.toast(@StringRes message: Int) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
}

fun Activity.toast(message:String){
    Toast.makeText(this,message, Toast.LENGTH_SHORT).show()
}

fun Activity.toast(@StringRes message: Int) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun View.snackbar(message: String, duration: Int = Snackbar.LENGTH_LONG) {
    Snackbar.make(this, message, duration).show()
}

fun View.snackbar(@StringRes message: Int, duration: Int = Snackbar.LENGTH_LONG) {
    Snackbar.make(this, message, duration).show()
}

fun View.snackbarWithAction(@StringRes message: Int, duration: Int = Snackbar.LENGTH_LONG,@StringRes textAction:Int ,action:()->Unit) {
    Snackbar.make(this, message, duration)
        .setAction(textAction) { action() }
        .show()
}