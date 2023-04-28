package com.example.navigationmenuudemy.ui.extension

import android.app.Activity
import android.app.AlertDialog
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.example.navigationmenuudemy.R

fun Activity.alertDialog(actionNegative: () -> Unit, titleMessage:Int) = AlertDialog.Builder(this)
    .setTitle(titleMessage)
    .setNegativeButton(R.string.txt_btn_delete) { _, _ ->
        actionNegative()
        //deleteProduct()
    }.setPositiveButton(R.string.txt_btn_cancel) { dialog, _ ->
        dialog.dismiss()
    }.show()

fun Fragment.alertDialog(actionNegative: () -> Unit, titleMessage:Int) = AlertDialog.Builder(this.requireContext())
    .setTitle(titleMessage)
    .setNegativeButton(R.string.txt_btn_delete) { _, _ ->
        actionNegative()
        //deleteProduct()
    }.setPositiveButton(R.string.txt_btn_cancel) { dialog, _ ->
        dialog.dismiss()
    }.show()