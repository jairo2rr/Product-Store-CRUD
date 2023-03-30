package com.example.navigationmenuudemy.ui.product

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.navigationmenuudemy.databinding.ActivityDetailProductBinding

class DetailProductActivity : AppCompatActivity() {
    companion object{
        const val PRODUCT_ID="PRODUCT_ID"
    }
    private lateinit var binding:ActivityDetailProductBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getIntExtra(PRODUCT_ID,0)
        binding.tvInfoProduct.text = "$id product"
    }
}