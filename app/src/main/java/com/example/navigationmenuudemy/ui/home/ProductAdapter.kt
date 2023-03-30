package com.example.navigationmenuudemy.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.navigationmenuudemy.databinding.FragmentHomeBinding
import com.example.navigationmenuudemy.databinding.ItemProductBinding
import com.example.navigationmenuudemy.domain.model.Category
import com.example.navigationmenuudemy.domain.model.Product

class ProductAdapter(
    private var listProducts: ArrayList<Product>,
    private val onItemClick:(Product)->Unit
) : RecyclerView.Adapter<ProductViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(listProducts[position])
        holder.itemView.setOnClickListener {
            onItemClick(listProducts[position])
        }
    }

    override fun getItemCount(): Int = listProducts.size
    fun updateList(list:ArrayList<Product>) {
        listProducts = list
        notifyDataSetChanged()
    }

}

class ProductViewHolder(private val binding: ItemProductBinding) : ViewHolder(binding.root) {
    fun bind(product: Product) {
        binding.tvProductId.text = product.id.toString()
        binding.tvProductName.text = product.product
    }
}