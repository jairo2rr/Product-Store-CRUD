package com.example.navigationmenuudemy.ui.product

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.navigationmenuudemy.databinding.ActivityDetailProductBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailProductActivity : AppCompatActivity() {
    companion object {
        const val PRODUCT_ID = "PRODUCT_ID"
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
            //TODO: Carga del progress bar
        }
        viewModel.product.observe(this) {

            binding.tvInfoProduct.text = "${it.id} product"
        }
    }
}