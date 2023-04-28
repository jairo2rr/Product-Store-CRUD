package com.example.navigationmenuudemy.ui.product

import android.app.AlertDialog
import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.navigationmenuudemy.R
import com.example.navigationmenuudemy.databinding.FragmentDialogAddSaleBinding
import com.example.navigationmenuudemy.domain.model.Product
import com.example.navigationmenuudemy.ui.extension.goneIf
import com.example.navigationmenuudemy.ui.extension.visibleIf
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DialogAddSaleFragment(private val product: Product, private val onAddSale: () -> Unit) :
    DialogFragment() {
    private lateinit var binding: FragmentDialogAddSaleBinding
    private val viewModel: DialogAddSaleViewModel by viewModels()
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = FragmentDialogAddSaleBinding.inflate(LayoutInflater.from(context))
        val builder = AlertDialog.Builder(requireActivity())
        builder.setView(binding.root)
        initComponents()
        initViewModels()
        return builder.create()

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initComponents() {
        with(binding) {
            btnDecreaseQuantity.setOnClickListener {
                decreaseNumber()
            }
            btnIncreaseQuantity.setOnClickListener {
                increaseNumber()
            }
            tvStock.text = "Stock: ${product.stock}"
            btnContinueSale.setOnClickListener {
                verifyCorrectQuantity()
            }
        }
    }

    private fun initViewModels(){
        viewModel.isCreated.observe(this){
            if(it){
                onAddSale()
                dismiss()
            }
        }
        viewModel.loading.observe(this){ isLoading ->
            binding.pbLoading visibleIf isLoading
            if(isLoading){
                binding.btnContinueSale.text = ""
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun verifyCorrectQuantity() {
        val valueInput = getValueNumber()
        if (valueInput == 0 || product.stock < valueInput) {
            binding.layoutCategoryName.error = getString(R.string.txt_field_stock_error)
            return
        }
        viewModel.intentCreateSale(product.id,valueInput)
    }

    private fun increaseNumber() {
        val valueInput = getValueNumber()
        binding.etCategoryName.setText("${valueInput.inc()}")
    }

    private fun decreaseNumber() {
        val valueInput = getValueNumber()
        if (valueInput > 0) {
            binding.etCategoryName.setText("${valueInput.dec()}")
        }
    }

    private fun getValueNumber(): Int {
        val textQuantity = binding.etCategoryName.text.toString()
        val numberQuantity: Int = if (textQuantity == "") 0 else textQuantity.toInt()
        if (numberQuantity == 0) {
            binding.etCategoryName.setText("$numberQuantity")
        }
        return numberQuantity
    }
}