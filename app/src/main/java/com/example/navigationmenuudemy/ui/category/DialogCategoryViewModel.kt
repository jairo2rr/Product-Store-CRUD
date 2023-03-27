package com.example.navigationmenuudemy.ui.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.navigationmenuudemy.data.StoreRepository
import com.example.navigationmenuudemy.domain.model.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DialogCategoryViewModel @Inject constructor(
    private var repository: StoreRepository
) : ViewModel() {

    private val _isCreated = MutableLiveData(false)
    val isCreated:LiveData<Boolean> = _isCreated
    fun createCategory(category:Category){
        viewModelScope.launch {
            repository.insertCategory(category)
            _isCreated.value = true
        }
    }

    suspend fun findCategoryXName(name:String): Boolean{
        return withContext(Dispatchers.IO){
            val list = repository.getCategoryXNameDB(name)
            list.isNotEmpty()
        }
    }

    fun editCategory(category: Category) {
        viewModelScope.launch{
            repository.updateCategory(category)
            _isCreated.value = true
        }
    }

}