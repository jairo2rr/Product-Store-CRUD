package com.example.navigationmenuudemy.ui.product

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.navigationmenuudemy.data.StoreRepository
import com.example.navigationmenuudemy.domain.model.Sale
import com.example.navigationmenuudemy.domain.model.SaleDescription
import com.example.navigationmenuudemy.ui.extension.isNull
import com.example.navigationmenuudemy.ui.extension.printToLog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class DialogAddSaleViewModel @Inject constructor(
    private val repository: StoreRepository,
) : ViewModel() {
    private val _isCreated = MutableLiveData<Boolean>(false)
    val isCreated:LiveData<Boolean> = _isCreated
    private val _loading = MutableLiveData<Boolean>(false)
    val loading: LiveData<Boolean> = _loading

    private suspend fun verifySaleExists(): Sale? {
        return withContext(Dispatchers.IO) {
            val response = repository.getSaleXState(0)
            response.firstOrNull()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun intentCreateSale(productId: Int, quantity: Int) {
        try {
            viewModelScope.launch {
                _loading.value = true
                verifySaleExists()?.let {
                    createSaleDescription(quantity, productId, it.id)
                    return@launch
                }
                createNewSale()?.let {
                    createSaleDescription(quantity, productId, it.id)
                }.also {
                    if(it.isNull){
                        Log.d("SALE_CREATE","$it get")
                    }
                }
            }
        } catch (exception: Exception) {
            println(exception.toString())
        } finally {
            _loading.value = false
        }

    }

    private fun createSaleDescription(quantity: Int, productId: Int, saleId: Int) {
        viewModelScope.launch {
            //1. Buscar si el producto ya esta en algun description
            val saleDescriptionFound = repository.getSaleDescriptionByProductId(productId,saleId)
            val product = repository.findProductFromId(productId).first()
            //2. Modificar la cantidad del description
            saleDescriptionFound.printToLog()
            if(saleDescriptionFound.isNotEmpty()){
                repository.upsertSaleDescription(saleDescriptionFound[0].copy(quantity = saleDescriptionFound[0].quantity + quantity))
            }else{
                val newSaleDescription = SaleDescription(quantity = quantity,
                    productId = productId,
                    saleId = saleId)
                repository.upsertSaleDescription(newSaleDescription)
            }
            repository.upsertProduct(product.copy(stock = (product.stock - quantity) ))
            _isCreated.value = true
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private suspend fun createNewSale():Sale? {
        val currentDate = LocalDate.now()
        val dateString = currentDate.toString()
        return withContext(Dispatchers.IO){
            repository.upsertSale(Sale(dateSale = dateString))
            verifySaleExists()
        }
    }

}