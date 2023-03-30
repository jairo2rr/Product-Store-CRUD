package com.example.navigationmenuudemy.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.navigationmenuudemy.data.StoreRepository
import com.example.navigationmenuudemy.domain.model.Category
import com.example.navigationmenuudemy.domain.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DialogProductViewModel @Inject constructor(
    private val repository: StoreRepository
):ViewModel() {

    fun createProduct(product: Product){
        viewModelScope.launch {
            repository.insertProduct(product)
        }
    }

    suspend fun getCategories(): List<Category>{
        return withContext(Dispatchers.IO){
            repository.getAllCategoriesDB()
        }
    }

}