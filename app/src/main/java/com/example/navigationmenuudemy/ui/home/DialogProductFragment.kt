package com.example.navigationmenuudemy.ui.home

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.navigationmenuudemy.R
import com.example.navigationmenuudemy.databinding.FragmentDialogProductBinding
import com.example.navigationmenuudemy.domain.model.Category
import com.example.navigationmenuudemy.domain.model.Product
import com.example.navigationmenuudemy.ui.extension.isEmpty
import com.example.navigationmenuudemy.ui.extension.printToLog
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DialogProductFragment(private val onCreateProduct: (Boolean) -> Unit) : DialogFragment() {
    private lateinit var binding: FragmentDialogProductBinding
    private val viewmodel: DialogProductViewModel by viewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = FragmentDialogProductBinding.inflate(LayoutInflater.from(context))

        val builder = AlertDialog.Builder(requireActivity())
        builder.setView(binding.root)
        binding.btnCancel.setOnClickListener { dismiss() }
        val items = arrayOf("Item 1", "Item 2", "Item 3", "Item 4")

        binding.btnAdd.setOnClickListener {
            if(checkErrorFields()){
                return@setOnClickListener
            }
            val name = binding.etProductName.text.toString()
            val price = binding.etProductPrice.text.toString()
            val stock = binding.etProductStock.text.toString()
            viewmodel.createProduct(Product(product = name,
                categoryId = 0,
                price = price.toFloat(),
                stock = stock.toInt()))
            onCreateProduct(true)
            dismiss()
        }
        return builder.create()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        loadCategories()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    private fun loadCategories() {
        lifecycleScope.launch {
            turnVisibilityElements(false)
            binding.pbLoading.visibility = View.VISIBLE
            val listCategories = viewmodel.getCategories().map { it.name }.toTypedArray()
            binding.pbLoading.visibility = View.GONE
            (binding.layoutProductCategory.editText as? MaterialAutoCompleteTextView)?.setSimpleItems(
                listCategories)
            turnVisibilityElements(true)
        }
    }

    private fun turnVisibilityElements(toShow: Boolean) {
        binding.layoutProductCategory.visibility = if (toShow) View.VISIBLE else View.GONE
        binding.layoutProductName.visibility = if (toShow) View.VISIBLE else View.GONE
        binding.layoutProductPrice.visibility = if (toShow) View.VISIBLE else View.GONE
        binding.layoutProductStock.visibility = if (toShow) View.VISIBLE else View.GONE
        binding.btnAdd.isEnabled = toShow
        binding.btnCancel.isEnabled = toShow
    }

    private fun checkErrorFields(): Boolean {
        val messageError = getString(R.string.txt_field_empty_error)
        val listCheck = mutableListOf<Boolean>()
        with(binding) {
            if (etProductName.isEmpty) {
                layoutProductName.error = messageError
                listCheck.add(true)
            }
            if (etProductPrice.isEmpty) {
                layoutProductPrice.error = messageError
                listCheck.add(true)
            }
            if (etProductStock.isEmpty) {
                layoutProductStock.error = messageError
                listCheck.add(true)
            }
        }
        return listCheck.contains(true)
    }

}