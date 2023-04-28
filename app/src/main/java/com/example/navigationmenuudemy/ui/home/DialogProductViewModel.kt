package com.example.navigationmenuudemy.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.navigationmenuudemy.data.StoreRepository
import com.example.navigationmenuudemy.domain.model.Category
import com.example.navigationmenuudemy.domain.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DialogProductViewModel @Inject constructor(
    private val repository: StoreRepository
):ViewModel() {
    private val _isCreated = MutableLiveData<Boolean>(false)
    val isCreated:LiveData<Boolean> = _isCreated
    private val _listCategories = MutableLiveData<List<Category>>(emptyList())
    val listCategories:LiveData<List<Category>> = _listCategories
    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading:LiveData<Boolean> = _isLoading


    fun createProduct(categoryName:String, name:String,price:Float,stock:Int,uri:String){
        val category = listCategories.value!!.find { category -> category.name == categoryName }
        val product = Product(product=name,price=price,stock=stock,uri=uri,categoryId=category!!.id)
        viewModelScope.launch {
            repository.upsertProduct(product)
            _isCreated.value = true
        }
    }
    fun loadCategories(){
        viewModelScope.launch {
            _isLoading.value = true
            _listCategories.value = repository.getAllCategoriesDB()
            _isLoading.value = false
        }
    }
    fun editProduct(product: Product, lastItemSelected: String) {
        val category = listCategories.value!!.find { category -> category.name == lastItemSelected }
        viewModelScope.launch{
            repository.upsertProduct(product.copy( categoryId = category!!.id ))
            _isCreated.value = true
        }
    }
}