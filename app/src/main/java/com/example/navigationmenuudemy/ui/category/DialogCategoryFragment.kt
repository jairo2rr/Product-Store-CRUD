package com.example.navigationmenuudemy.ui.category

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.navigationmenuudemy.R
import com.example.navigationmenuudemy.databinding.FragmentDialogCategoryBinding
import com.example.navigationmenuudemy.domain.model.Category
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DialogCategoryFragment(private val onCreateCategory:(Boolean)->Unit): DialogFragment() {

    private lateinit var binding: FragmentDialogCategoryBinding
    private val viewmodel:DialogCategoryViewModel by viewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = FragmentDialogCategoryBinding.inflate(LayoutInflater.from(context))

        val builder = AlertDialog.Builder(requireActivity())
        builder.setView(binding.root)
        binding.btnCancel.setOnClickListener { dismiss() }
        binding.btnAdd.setOnClickListener {
            val name = binding.etCategoryName.text.toString()
            viewmodel.createCategory(Category(name))
            onCreateCategory(true)
            dismiss()
        }
        return builder.create()
    }

}