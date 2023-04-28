package com.example.navigationmenuudemy.ui.sale

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.navigationmenuudemy.databinding.ItemDetailSaleBinding
import com.example.navigationmenuudemy.domain.model.Product
import com.example.navigationmenuudemy.domain.model.SaleDescription

class SaleDetailAdapter(
    var listSaleDescriptions: List<SaleDescription>,
    var listProducts: List<Product>,
    private val onQuantityModify:(Int,Int)->Unit
) :
    RecyclerView.Adapter<SaleDetailViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SaleDetailViewHolder {
        val binding =
            ItemDetailSaleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SaleDetailViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SaleDetailViewHolder, position: Int) {
        holder.bind(listSaleDescriptions[position], listProducts[position],onQuantityModify)
    }

    override fun getItemCount(): Int = listProducts.size

}

class SaleDetailViewHolder(private val binding: ItemDetailSaleBinding) : ViewHolder(binding.root) {

    fun bind(saleDescription: SaleDescription, product: Product, onQuantityModify: (Int,Int) -> Unit) {
        binding.tvNameProduct.text = "${product.product}"
        binding.tvPriceProduct.text = "S/. ${product.price}"
        binding.tvQuantityProduct.text = "${saleDescription.quantity}"
        binding.btnDecreaseQntyProduct.setOnClickListener { decreaseQuantity(onQuantityModify,saleDescription.id) }
        binding.btnIncreaseQntyProduct.setOnClickListener { increaseQuantity(onQuantityModify,saleDescription.id) }
    }

    private fun increaseQuantity(onQuantityModify: (Int, Int) -> Unit, id: Int) {
        val quantity = binding.tvQuantityProduct.text.toString().toInt()
        binding.tvQuantityProduct.text = "${quantity.inc()}"
        onQuantityModify(quantity.inc(), id)

    }

    private fun decreaseQuantity(onQuantityModify: (Int, Int) -> Unit, id: Int) {
        val quantity = binding.tvQuantityProduct.text.toString().toInt()
        if (quantity != 0) {
            binding.tvQuantityProduct.text = "${quantity.dec()}"
            onQuantityModify(quantity.dec(),id)
        }
    }
}