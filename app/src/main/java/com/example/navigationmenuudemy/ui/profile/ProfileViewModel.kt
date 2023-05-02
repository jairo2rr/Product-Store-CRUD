package com.example.navigationmenuudemy.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.navigationmenuudemy.data.StoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: StoreRepository
) : ViewModel() {
    private val _pairScores = MutableLiveData<Pair<Float,Int>>()
    val pairScores: LiveData<Pair<Float, Int>> = this._pairScores

    init {
        getScoresGeneral()
    }

    private fun getScoresGeneral() {
        viewModelScope.launch {
            val totalUtility = 0.0f
            val numberSales = repository.getCountSales()
            _pairScores.value = Pair(totalUtility,numberSales)
        }
    }
}