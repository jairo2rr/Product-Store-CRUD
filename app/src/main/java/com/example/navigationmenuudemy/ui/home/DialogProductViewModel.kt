package com.example.navigationmenuudemy.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
    private val _isCreated = MutableLiveData<Boolean>(false)
    val isCreated:LiveData<Boolean> = _isCreated
    private var listCategories = emptyList<Category>()
    fun createProduct(categoryName:String, name:String,price:Float,stock:Int,uri:String):Boolean{
        val category = listCategories.find { category -> category.name == categoryName } ?: return false
        val product = Product(product=name,price=price,stock=stock,uri=uri,categoryId=category.id)
        viewModelScope.launch {
            repository.insertProduct(product)
            _isCreated.value = true
        }
        return true
    }

    suspend fun getCategories(): List<Category>{
        return withContext(Dispatchers.IO){
            listCategories = repository.getAllCategoriesDB()
            listCategories
        }
    }

}