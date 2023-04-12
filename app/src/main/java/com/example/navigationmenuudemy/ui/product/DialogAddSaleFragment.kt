package com.example.navigationmenuudemy.ui.product

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import com.example.navigationmenuudemy.databinding.FragmentDialogAddSaleBinding

class DialogAddSaleFragment:DialogFragment() {
    private lateinit var binding:FragmentDialogAddSaleBinding
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = FragmentDialogAddSaleBinding.inflate(LayoutInflater.from(context))
        val builder = AlertDialog.Builder(requireActivity())
        builder.setView(binding.root)
        initComponents()
        return builder.create()

    }

    private fun initComponents(){
        with(binding){
            btnDecreaseQuantity.setOnClickListener {
                decreaseNumber()
            }
            btnIncreaseQuantity.setOnClickListener {
                increaseNumber()
            }
        }
    }

    private fun increaseNumber() {
        val textQuantity = binding.etCategoryName.text.toString()
        val numberQuantity:Int = if(textQuantity == "") 0 else textQuantity.toInt()
        binding.etCategoryName.setText("${numberQuantity.inc()}")
    }

    private fun decreaseNumber() {
        val textQuantity = binding.etCategoryName.text.toString()
        val numberQuantity:Int = if(textQuantity == "") 0 else textQuantity.toInt()
        binding.etCategoryName.setText("${if(numberQuantity>0) numberQuantity.dec() else numberQuantity}")
    }
}