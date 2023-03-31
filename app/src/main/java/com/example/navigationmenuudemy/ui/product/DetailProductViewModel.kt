package com.example.navigationmenuudemy.ui.product

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
}