package com.example.navigationmenuudemy.ui.category

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.navigationmenuudemy.R
import com.example.navigationmenuudemy.databinding.FragmentDialogCategoryBinding
import com.example.navigationmenuudemy.domain.model.Category
import com.example.navigationmenuudemy.ui.extension.isNull
import com.example.navigationmenuudemy.ui.extension.printToLog
import com.example.navigationmenuudemy.ui.extension.toast
import com.example.navigationmenuudemy.ui.extension.upperFirstChar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DialogCategoryFragment(
    private val onCreateCategory: (Boolean) -> Unit,
    private val category: Category?,
) : DialogFragment() {

    private lateinit var binding: FragmentDialogCategoryBinding
    private val viewmodel: DialogCategoryViewModel by viewModels()
    private var errorFound = false

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = FragmentDialogCategoryBinding.inflate(LayoutInflater.from(context))

        val builder = AlertDialog.Builder(requireActivity())
        builder.setView(binding.root)
        if (category != null) {
            binding.etCategoryName.setText(category.name)
            binding.btnAdd.text = getString(R.string.txt_btn_update)
        }
        binding.btnCancel.setOnClickListener { dismiss() }
        binding.etCategoryName.doOnTextChanged { text, _, _, _ ->
            if (!text.isNullOrEmpty() && errorFound) {
                setTextError(null)
            }
        }
        binding.btnAdd.setOnClickListener {
            val name = binding.etCategoryName.text.toString().upperFirstChar.trim()
            if (name.isEmpty()) {
                errorFound = true
                binding.layoutCategoryName.error.printToLog()
                setTextError(getString(R.string.txt_field_empty_error))
            } else {
                binding.layoutCategoryName.error = null
                lifecycleScope.launch {
                    if (viewmodel.findCategoryXName(name)) {
                        toast("La categoría ya existe!")
                        return@launch
                    }
                    if (category.isNull) {
                        viewmodel.createCategory(Category(name = name))
                        return@launch
                    }
                    viewmodel.editCategory(category!!.apply { this.name = name })
                }
            }
        }
        viewmodel.isCreated.observe(this) {
            if (it) {
                onCreateCategory(it)
                dismiss()
            }
        }
        return builder.create()
    }

    private fun setTextError(text:String?){
        binding.layoutCategoryName.error = text
    }
}

