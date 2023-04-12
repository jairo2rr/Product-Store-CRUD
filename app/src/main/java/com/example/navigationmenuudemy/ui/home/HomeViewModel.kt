package com.example.navigationmenuudemy.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.navigationmenuudemy.data.StoreRepository
import com.example.navigationmenuudemy.domain.model.Category
import com.example.navigationmenuudemy.domain.model.Product
import com.example.navigationmenuudemy.ui.extension.printToLog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: StoreRepository,
) : ViewModel() {
    private val _listLoaded = MutableLiveData<List<Product>>()

    private val _listProducts = MutableLiveData<List<Product>>()
    val listProducts: LiveData<List<Product>> = _listProducts

    init {
        uploadProducts()
    }

    fun uploadProducts() {
        viewModelScope.launch {
            _listLoaded.value = repository.getAllProductsDB()
            updateList()
        }
    }

    fun searchProducts(wordSearch: String) {
        viewModelScope.launch {
            _listProducts.value =
                _listLoaded.value?.filter { it.product.contains(wordSearch) } ?: emptyList()
        }
    }

    fun updateList() {
        _listProducts.value = _listLoaded.value
    }

    fun filterList(filter: String) {
        when(filter){
            "favorites"->
                _listProducts.value = _listLoaded.value!!.filter { it.favorite }
            else -> "NOFILTER".printToLog()
        }
    }

}