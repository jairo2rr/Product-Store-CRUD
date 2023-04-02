package com.example.navigationmenuudemy.ui.product

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.example.navigationmenuudemy.databinding.ActivityDetailProductBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailProductActivity : AppCompatActivity() {
    companion object {
        const val PRODUCT_ID = "PRODUCT_ID"
        const val REQUEST_CODE = 1
    }

    private val viewModel: DetailProductViewModel by viewModels()
    private lateinit var binding: ActivityDetailProductBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val id = intent.getIntExtra(PRODUCT_ID, 0)
        viewModel.findProductFromId(id)
        viewModel.loading.observe(this) {
            binding.pbLoading.visibility = if (it) View.VISIBLE else View.GONE
        }
        viewModel.product.observe(this) { product ->
            supportActionBar?.title = product.product
            binding.tvInfoProduct.text = "${product.id} - ${product.product}"
            initOpenDocumentLauncher(product.uri)
            binding.tvProductDescription.text =
                StringBuilder().appendLine("Categoría: ${product.categoryId}")
                    .appendLine("Precio: ${product.price}").append("Stock: ${product.stock}")
        }
    }

    private fun initOpenDocumentLauncher(txtUri:String) {
        val uri =  Uri.parse(txtUri)
        binding.imageView.setImageURI(uri)
    }

}