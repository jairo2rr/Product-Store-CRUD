package com.example.navigationmenuudemy.ui.category

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.navigationmenuudemy.databinding.ItemCategoryBinding
import com.example.navigationmenuudemy.domain.model.Category
import java.time.LocalDate

class CategoryAdapter(
    private var listCategories: ArrayList<Category>,
    private val onEditClick: (Category) -> Unit,
    private val onDeleteClick: (Category) -> Unit,
) : RecyclerView.Adapter<CategoryViewHolder>() {
    private var lastPosition = -1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding =
            ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {

        val category = listCategories[position]
        holder.bind(category, onEditClick, onDeleteClick)
    }

    override fun getItemCount(): Int = listCategories.size
    fun getList():ArrayList<Category> = listCategories
    fun setList(newList:ArrayList<Category>){
        listCategories = newList
        notifyDataSetChanged()
    }

    fun removeItem(category: Category){
        lastPosition = listCategories.indexOf(category)
        listCategories.removeAt(lastPosition)
        Log.d("CategoryAdapter","Position:$lastPosition & updateList:${listCategories.indices}")
        notifyItemRemoved(lastPosition)
    }

    fun restoreCategory(category: Category) {
        listCategories.add(lastPosition,category)
        notifyItemInserted(lastPosition)
    }

}

class CategoryViewHolder(private val binding: ItemCategoryBinding) : ViewHolder(binding.root) {

    fun bind(
        category: Category,
        onEditClick: (Category) -> Unit,
        onDeleteClick: (Category) -> Unit,
    ) {
        binding.tvCategoryName.text = category.name
        binding.btnEditCategory.setOnClickListener { onEditClick(category) }
        binding.btnDeleteCategory.setOnClickListener{ onDeleteClick(category) }
    }
}