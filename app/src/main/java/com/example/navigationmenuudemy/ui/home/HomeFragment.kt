package com.example.navigationmenuudemy.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.navigationmenuudemy.R
import com.example.navigationmenuudemy.databinding.FragmentHomeBinding
import com.example.navigationmenuudemy.domain.model.Product
import com.example.navigationmenuudemy.ui.category.DialogCategoryFragment
import com.example.navigationmenuudemy.ui.product.DetailProductActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel:HomeViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = ProductAdapter(arrayListOf()){
            startProductActivity(it)
        }

        binding.rvProducts.adapter = adapter
        viewModel.listProducts.observe(viewLifecycleOwner){
            adapter.updateList(ArrayList(it))
        }
        binding.fabAddProduct.setOnClickListener {
            openDialogProductFragment()
        }
    }

    private fun startProductActivity(product:Product) {
        val intent = Intent(context,DetailProductActivity::class.java)
        intent.apply {
            putExtra(DetailProductActivity.PRODUCT_ID,product.id)
        }
        startActivity(intent)
    }

    private fun openDialogProductFragment() {

        val dialog = DialogProductFragment(){
            canUpdate(it)
        }

        dialog.show(parentFragmentManager, "showDialogProduct")
    }
    private fun canUpdate(answer: Boolean) {
        if (answer) {
            viewModel.uploadProducts()
        }
    }

}