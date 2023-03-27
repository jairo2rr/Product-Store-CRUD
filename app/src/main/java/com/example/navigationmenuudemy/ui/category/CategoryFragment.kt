package com.example.navigationmenuudemy.ui.category

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.navigationmenuudemy.databinding.FragmentCategoryBinding
import com.example.navigationmenuudemy.domain.model.Category
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryFragment : Fragment() {

    private lateinit var binding: FragmentCategoryBinding
    private val viewmodel: CategoryViewModel by viewModels()
    private var lastCategoryDeleted:Category? = null

    private lateinit var adapter:CategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCategoryBinding.inflate(inflater, container, false)
//        return inflater.inflate(R.layout.fragment_category, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnAddCategory.setOnClickListener {
            openCreateNewOneDialog()
        }
        adapter = CategoryAdapter(arrayListOf(), { category ->
            openCreateNewOneDialog(category)
        }, { category ->
            onDeleteCategory(category)
        })
        binding.rvCategories.adapter = adapter
        viewmodel.listCategories.observe(viewLifecycleOwner) {
            Log.d("VALUECATEGORY", "UPDATELIST: $it")
            adapter.setList(ArrayList(it))
        }
    }

    private fun onDeleteCategory(category: Category) {
        lastCategoryDeleted = category
        val position = adapter.removeItem(category)
        var isRestored = false
        Snackbar.make(binding.root, "${category.name} was deleted.", Snackbar.LENGTH_SHORT)
            .setAction("Cancel") {
                isRestored = true
                adapter.restoreCategory(category)
            }.addCallback(object :Snackbar.Callback(){
                override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                    super.onDismissed(transientBottomBar, event)
                    if(!isRestored){
                        viewmodel.deleteCategory(category)
                        //viewmodel.updateListCategories()
                    }
                }
            })
            .show()
//        .makeText(context,"${category.name} was deleted.",Toast.LENGTH_SHORT).show()
    }

    private fun openCreateNewOneDialog(category: Category? = null) {
        val dialog = DialogCategoryFragment({
            Log.d("VALUECATEGORY", "GET: $it")
            canUpdate(it)
        }, category = category)
        dialog.show(parentFragmentManager, "showDialog")
    }

    private fun canUpdate(answer: Boolean) {
        if (answer) {
            viewmodel.updateListCategories()
        }
    }

}