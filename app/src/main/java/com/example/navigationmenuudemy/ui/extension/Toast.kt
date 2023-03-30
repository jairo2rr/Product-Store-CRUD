package com.example.navigationmenuudemy.ui.extension

import android.app.Activity
import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.toast(message:String){
    Toast.makeText(requireContext(),message, Toast.LENGTH_SHORT).show()
}

fun Activity.toast(message:String){
    Toast.makeText(this,message, Toast.LENGTH_SHORT).show()
}