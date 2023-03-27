package com.example.navigationmenuudemy.ui.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.navigationmenuudemy.data.StoreRepository
import com.example.navigationmenuudemy.domain.model.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val repository: StoreRepository
) : ViewModel() {
    private val _listCategories = MutableLiveData<List<Category>>()
    val listCategories: LiveData<List<Category>> = _listCategories
    
    init {
        updateListCategories()
    }

    fun updateListCategories(){
        viewModelScope.launch {
            _listCategories.value = repository.getAllCategoriesDB()
        }
    }

    fun deleteCategory(category: Category) {
        viewModelScope.launch {
            repository.deleteCategory(category)
        }
    }

}