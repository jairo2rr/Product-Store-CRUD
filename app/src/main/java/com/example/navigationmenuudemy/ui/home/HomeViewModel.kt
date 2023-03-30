package com.example.navigationmenuudemy.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.navigationmenuudemy.data.StoreRepository
import com.example.navigationmenuudemy.domain.model.Category
import com.example.navigationmenuudemy.domain.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: StoreRepository
):ViewModel() {
    private val _listProducts = MutableLiveData<List<Product>>()
    val listProducts: LiveData<List<Product>> = _listProducts


}