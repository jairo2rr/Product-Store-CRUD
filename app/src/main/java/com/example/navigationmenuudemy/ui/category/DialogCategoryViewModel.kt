package com.example.navigationmenuudemy.ui.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.navigationmenuudemy.data.StoreRepository
import com.example.navigationmenuudemy.data.database.entities.CategoryEntity
import com.example.navigationmenuudemy.domain.model.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DialogCategoryViewModel @Inject constructor(
    private var repository: StoreRepository
) : ViewModel() {

    fun createCategory(category:Category){
        viewModelScope.launch {
            repository.insertCategory(category)
        }
    }
}