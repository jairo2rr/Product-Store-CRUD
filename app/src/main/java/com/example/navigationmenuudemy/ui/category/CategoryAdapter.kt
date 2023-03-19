package com.example.navigationmenuudemy.ui.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.navigationmenuudemy.databinding.ItemCategoryBinding
import com.example.navigationmenuudemy.domain.model.Category

class CategoryAdapter(var listCategories:List<Category>):RecyclerView.Adapter<CategoryViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(listCategories[position])
    }

    override fun getItemCount(): Int = listCategories.size

}

class CategoryViewHolder(private val binding: ItemCategoryBinding):ViewHolder(binding.root){

    fun bind(category: Category){
        binding.tvCategoryName.text = category.name
    }
}