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
    private val _loading = MutableLiveData<Boolean>()
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
                _loading.value = false
                _isCreated.value = true
            }
        } catch (exception: Exception) {
            println(exception.toString())
        }

    }

    private fun createSaleDescription(quantity: Int, productId: Int, saleId: Int) {
        viewModelScope.launch {
            //Crear con los parametros requeridos
            repository.insertSaleDescription(SaleDescription(quantity = quantity,
                productId = productId,
                saleId = saleId))
            val product = repository.findProductFromId(productId).first()
            repository.updateProduct(product.copy(stock = (product.stock - quantity) ))

        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private suspend fun createNewSale():Sale? {
        val currentDate = LocalDate.now()
        val dateString = currentDate.toString()
        return withContext(Dispatchers.IO){
            repository.insertSale(Sale(dateSale = dateString))
            verifySaleExists()
        }
    }

}