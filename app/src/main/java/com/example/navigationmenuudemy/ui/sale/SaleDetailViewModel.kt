package com.example.navigationmenuudemy.ui.sale

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.navigationmenuudemy.data.StoreRepository
import com.example.navigationmenuudemy.data.database.entities.SaleWithDescriptions
import com.example.navigationmenuudemy.domain.model.Product
import com.example.navigationmenuudemy.domain.model.Sale
import com.example.navigationmenuudemy.domain.model.SaleDescription
import com.example.navigationmenuudemy.domain.model.toDomain
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.function.BiPredicate
import javax.inject.Inject

@HiltViewModel
class SaleDetailViewModel @Inject constructor(
    private val repository: StoreRepository,
) : ViewModel() {

    private val _saleFound = MutableLiveData<Sale>()
    val saleFound: LiveData<Sale> = _saleFound

    private val _saleDescriptions = MutableLiveData<List<SaleDescription>>()
    val saleDescriptions: LiveData<List<SaleDescription>> = _saleDescriptions

    private val _subTotal = MutableLiveData<Float>()
    val subTotal:LiveData<Float> = _subTotal
    
    private val _saleProducts = MutableLiveData<List<Product>>()
    val saleProducts:LiveData<List<Product>> = _saleProducts
    
    init {
        findSaleActive()
    }

    private fun findSaleActive() {
        viewModelScope.launch {
            _saleFound.value = repository.getSaleXState(0).firstOrNull()
            saleFound.value?.let {
                getSaleDescriptions(it.id)
            }
            operateSubTotal()
        }
    }

    private suspend fun getSaleDescriptions(saleId:Int){
        _saleDescriptions.value = repository.getSaleWithDescriptions(saleId)
            .first().saleDescriptions.map { description -> description.toDomain() }
    }

    private fun operateSubTotal(){
        viewModelScope.launch {
            var extraSubTotal = 0.0f
            val listProducts = mutableListOf<Product>()
            saleDescriptions.value?.forEach {
                val product = repository.findProductFromId(it.productId).first()
                listProducts.add(product)
                extraSubTotal += product.price*it.quantity
            }
            _subTotal.value = extraSubTotal
            _saleProducts.value = listProducts.toList()
        }
    }


    suspend fun cancelSale():Boolean {
        return withContext(Dispatchers.IO){
            repository.upsertSale(_saleFound.value!!.copy(state = 2))
            true
        }
    }

    suspend fun registerSale():Boolean{
        return withContext(Dispatchers.IO){
            repository.upsertSale(_saleFound.value!!.copy(state = 1))
            true
        }
    }

    fun modifyQuantity(modify: Int, id: Int) {
        viewModelScope.launch {
            if(modify == 0){
                repository.deleteSaleDescription(_saleDescriptions.value!!.find { it.id == id }!!)
            }else{
                repository.upsertSaleDescription(_saleDescriptions.value!!.find { it.id == id }!!.copy(quantity = modify))
            }
            getSaleDescriptions(saleFound.value!!.id)
            operateSubTotal()
        }
    }
}