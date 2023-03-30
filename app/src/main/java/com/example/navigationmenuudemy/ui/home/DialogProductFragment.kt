package com.example.navigationmenuudemy.ui.home

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.navigationmenuudemy.R
import com.example.navigationmenuudemy.databinding.FragmentDialogProductBinding
import com.example.navigationmenuudemy.ui.extension.isEmpty
import com.example.navigationmenuudemy.ui.extension.printToLog
import com.example.navigationmenuudemy.ui.extension.toast
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DialogProductFragment(private val onCreateProduct: (Boolean) -> Unit) : DialogFragment() {
    private lateinit var binding: FragmentDialogProductBinding
    private val viewmodel: DialogProductViewModel by viewModels()
    private val listCheck = mutableListOf<Boolean>(false,false,false,false)
    private var lastItemSelected:String? = null
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = FragmentDialogProductBinding.inflate(LayoutInflater.from(context))

        val builder = AlertDialog.Builder(requireActivity())
        builder.setView(binding.root)
        binding.btnCancel.setOnClickListener { dismiss() }
        binding.btnAdd.setOnClickListener {
            if(checkErrorFields()){
                return@setOnClickListener
            }
            val name = binding.etProductName.text.toString()
            val price = binding.etProductPrice.text.toString().toFloat()
            val stock = binding.etProductStock.text.toString().toInt()
            val response = viewmodel.createProduct(lastItemSelected!!,name,price,stock)
            if(!response){
                toast("Ocurrió un error, intente denuevo.")
                return@setOnClickListener
            }
            dismiss()
        }
        binding.atvSelectCategory.setOnItemClickListener { parent, _, position, _ ->
            val item = parent.getItemAtPosition(position).toString()
            lastItemSelected = item
            if(listCheck[0]){
                binding.layoutProductCategory.error = null
                binding.layoutProductCategory.isErrorEnabled = false
            }
            item.printToLog()
        }
        setEventoForFields()
        viewmodel.isCreated.observe(this) {
            if (it) {
                onCreateProduct(it)
                dismiss()
            }
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

    private fun setEventoForFields(){
        with(binding){
            etProductName.doOnTextChanged { text, _, _, _ ->
                if (!text.isNullOrEmpty() && listCheck[1]) {
                    layoutProductName.error = null
                    layoutProductName.isErrorEnabled = false
                }
            }
            etProductPrice.doOnTextChanged { text, _, _, _ ->
                if (!text.isNullOrEmpty() && listCheck[2]) {
                    layoutProductPrice.error = null
                    layoutProductPrice.isErrorEnabled = false
                }
            }
            etProductStock.doOnTextChanged { text, _, _, _ ->
                if (!text.isNullOrEmpty() && listCheck[3]) {
                    layoutProductStock.error = null
                    layoutProductStock.isErrorEnabled = false
                }
            }
        }
    }

    private fun checkErrorFields(): Boolean {
        val messageError = getString(R.string.txt_field_empty_error)
        listCheck.clear()
        with(binding) {
            val isTheSame = atvSelectCategory.text.toString().trim() == lastItemSelected
            val atCondition = lastItemSelected!=null && isTheSame
            if(!isTheSame){
                atvSelectCategory.setText("")
            }
            if(!atCondition){
                layoutProductCategory.error = messageError
            }
            if (etProductName.isEmpty) {
                layoutProductName.error = messageError
            }
            if (etProductPrice.isEmpty) {
                layoutProductPrice.error = messageError
            }
            if (etProductStock.isEmpty) {
                layoutProductStock.error = messageError
            }
            listCheck.addAll(arrayOf(!atCondition,etProductName.isEmpty,etProductPrice.isEmpty,etProductStock.isEmpty))
        }
        return listCheck.contains(true)
    }

}