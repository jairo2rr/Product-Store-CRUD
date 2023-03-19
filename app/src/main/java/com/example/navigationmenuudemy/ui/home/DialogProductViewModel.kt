package com.example.navigationmenuudemy.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.navigationmenuudemy.data.StoreRepository
import com.example.navigationmenuudemy.domain.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
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

}