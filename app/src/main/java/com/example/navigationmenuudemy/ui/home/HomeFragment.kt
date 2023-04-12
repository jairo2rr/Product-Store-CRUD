package com.example.navigationmenuudemy.ui.home

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.navigationmenuudemy.R
import com.example.navigationmenuudemy.databinding.FragmentHomeBinding
import com.example.navigationmenuudemy.domain.model.Product
import com.example.navigationmenuudemy.ui.extension.printToLog
import com.example.navigationmenuudemy.ui.extension.snackbar
import com.example.navigationmenuudemy.ui.extension.snackbarWithAction
import com.example.navigationmenuudemy.ui.extension.toast
import com.example.navigationmenuudemy.ui.product.DetailProductActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = ProductAdapter(arrayListOf()) {
            startProductActivity(it)
        }

        binding.rvProducts.adapter = adapter
        viewModel.listProducts.observe(viewLifecycleOwner) {
            adapter.updateList(ArrayList(it))
        }
        binding.fabAddProduct.setOnClickListener {
            openDialogProductFragment()
            //requestPermission()
        }
        initOnClickChips()
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(text: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(text: String?): Boolean {
                if (text != null && text.trim() != "") {
                    viewModel.searchProducts(text)
                }
                if (text != null && text.trim() == "") {
                    viewModel.updateList()
                }
                return true
            }

        })
    }

    private fun initOnClickChips() {
        with(binding) {
            chFavorites.setOnClickListener {
                if (!chFavorites.isChecked ){
                    viewModel.updateList()
                    return@setOnClickListener
                }
                viewModel.filterList("favorites")
            }
        }
    }

    private fun startProductActivity(product: Product) {
        val intent = Intent(context, DetailProductActivity::class.java)
        intent.apply {
            putExtra(DetailProductActivity.PRODUCT_ID, product.id)
        }
        startActivity(intent)
    }

    //    private fun requestPermission() {
//        requestPermissionLauncher.launch(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE))
//    }
    private fun openDialogProductFragment() {

        val dialog =
            DialogProductFragment(onCreateProduct = {
                canUpdate(it)
            })
        dialog.show(parentFragmentManager, "showDialogProduct")
    }

    private fun canUpdate(answer: Boolean) {
        if (answer) {
            viewModel.uploadProducts()
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.uploadProducts()
    }
}