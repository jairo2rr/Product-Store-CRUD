package com.example.navigationmenuudemy.domain.model

import com.example.navigationmenuudemy.data.database.entities.SaleEntity
import java.util.Date

data class Sale(
    val id:Int=0,
    var dateSale:Date,
    var state:Byte
)

fun SaleEntity.toDomain() = Sale(
    id = id,
    dateSale = dateSale,
    state = state
)