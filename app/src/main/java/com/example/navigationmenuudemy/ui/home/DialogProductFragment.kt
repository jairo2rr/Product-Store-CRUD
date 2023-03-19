package com.example.navigationmenuudemy.ui.home

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.navigationmenuudemy.databinding.FragmentDialogProductBinding
import com.example.navigationmenuudemy.domain.model.Category
import com.example.navigationmenuudemy.domain.model.Product

class DialogProductFragment(private val onCreateProduct:(Boolean)->Unit):DialogFragment() {
    private lateinit var binding:FragmentDialogProductBinding
    private val viewmodel:DialogProductViewModel by viewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = FragmentDialogProductBinding.inflate(LayoutInflater.from(context))

        val builder = AlertDialog.Builder(requireActivity())
        builder.setView(binding.root)
        binding.btnCancel.setOnClickListener { dismiss() }
        binding.btnAdd.setOnClickListener {
            val category = binding.etProductCategory.text.toString()
            val name = binding.etProductName.text.toString()
            val price = binding.etProductPrice.text.toString()
            val stock = binding.etProductStock.text.toString()
            viewmodel.createProduct(Product(product = name, categoryId = category.toInt(), price = price.toFloat(), stock = stock.toInt()))
            onCreateProduct(true)
            dismiss()
        }
        return builder.create()
    }
}