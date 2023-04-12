package com.example.navigationmenuudemy.domain.model

import androidx.room.ColumnInfo
import com.example.navigationmenuudemy.data.database.entities.SaleDescriptionEntity
import com.example.navigationmenuudemy.data.database.entities.SaleEntity

data class SaleDescription(
    val id:Int=0,
    var quantity:Int,
    var discount:Float,
    val saleId:Int,
    val productId:Int
)

fun SaleDescriptionEntity.toDomain() = SaleDescription(
    id=id,
    quantity=quantity,
    discount=discount,
    saleId=saleId,
    productId=productId
)