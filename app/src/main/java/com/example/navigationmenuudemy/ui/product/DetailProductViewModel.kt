package com.example.navigationmenuudemy.ui.product

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.navigationmenuudemy.data.StoreRepository
import com.example.navigationmenuudemy.domain.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailProductViewModel @Inject constructor(
    private val repository: StoreRepository
):ViewModel() {

    private val _product = MutableLiveData<Product>()
    val product: LiveData<Product> = _product

    private val _loading = MutableLiveData<Boolean>(false)
    val loading:LiveData<Boolean> = _loading

    fun findProductFromId(productId:Int){
        viewModelScope.launch {
            _loading.value = true
            _product.value = repository.findProductFromId(productId).first()
            _loading.value = false
        }
    }

    fun updateInfoProduct(){
        viewModelScope.launch {
            _loading.value = true
            _product.value = repository.findProductFromId(product.value!!.id).first()
            _loading.value = false
        }
    }

    fun deleteProduct() {
        viewModelScope.launch {
            _loading.value = true
            repository.deleteProduct(product.value!!)
            _loading.value = false
        }
    }

    fun setFavorite() {
        val newValue = !product.value!!.favorite
        Log.d("FAVORITE_VALUE","value: $newValue")
        viewModelScope.launch{
            _loading.value = true
            repository.upsertProduct(product.value!!.copy( favorite = newValue ))
            updateInfoProduct()
            _loading.value = false
        }

    }
}