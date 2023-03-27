package com.example.navigationmenuudemy.ui.category

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.navigationmenuudemy.R
import com.example.navigationmenuudemy.databinding.FragmentDialogCategoryBinding
import com.example.navigationmenuudemy.domain.model.Category
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
@AndroidEntryPoint
class DialogCategoryFragment(private val onCreateCategory:(Boolean)->Unit, private val category:Category?): DialogFragment() {

    private lateinit var binding: FragmentDialogCategoryBinding
    private val viewmodel:DialogCategoryViewModel by viewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = FragmentDialogCategoryBinding.inflate(LayoutInflater.from(context))

        val builder = AlertDialog.Builder(requireActivity())
        builder.setView(binding.root)
        if(category!= null){
            binding.etCategoryName.setText(category.name)
            binding.btnAdd.text = getString(R.string.txt_btn_update)
        }
        binding.btnCancel.setOnClickListener { dismiss() }
        binding.btnAdd.setOnClickListener {
            val name = binding.etCategoryName.text.toString().let {
                it.lowercase().replaceFirstChar { first -> first.uppercase() }
            }
            lifecycleScope.launch {
                if (viewmodel.findCategoryXName(name)){
                    requireActivity().toast("La categoria ya existe!")
                    return@launch
                }
                if(category != null){
                    viewmodel.editCategory(category.apply { this.name = name })
                }else{
                    viewmodel.createCategory(Category(name = name))
                }
            }
            /*val exist = lifecycleScope.async {
                viewmodel.findCategoryXName(name)
            }
            exist.invokeOnCompletion {
                if(it == null){
                    if(exist.getCompleted()){
                        requireActivity().toast("La categoria ya existe!")
                    }else{
                        viewmodel.createCategory(Category(name))
                        onCreateCategory(true)
                        dismiss()
                    }
                }
            }*/
        }
        viewmodel.isCreated.observe(this){
            if(it){
                onCreateCategory(it)
                dismiss()
            }
        }
        return builder.create()
    }
}

fun Context.toast(message:String){
    Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
}