package com.example.navigationmenuudemy.ui.category

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import com.example.navigationmenuudemy.R
import com.example.navigationmenuudemy.databinding.FragmentCategoryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryFragment : Fragment() {

    private lateinit var binding: FragmentCategoryBinding
    private val viewmodel:CategoryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCategoryBinding.inflate(inflater,container,false)
//        return inflater.inflate(R.layout.fragment_category, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnAddCategory.setOnClickListener {
            openCreateNewOneDialog()
        }
        val adapter = CategoryAdapter(emptyList())
        binding.rvCategories.adapter = adapter
        viewmodel.listCategories.observe(viewLifecycleOwner){
            adapter.listCategories = it
            adapter.notifyDataSetChanged()
        }
    }

    private fun openCreateNewOneDialog() {
        val dialog = DialogCategoryFragment {
            if(it)
                viewmodel.updateListCategories()
        }
        dialog.show(parentFragmentManager, "showDialog")
    }

}