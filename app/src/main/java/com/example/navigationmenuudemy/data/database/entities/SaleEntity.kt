package com.example.navigationmenuudemy.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.navigationmenuudemy.domain.model.Sale
import java.util.Date

@Entity(tableName = "sale_table")
data class SaleEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id:Int=0,
    @ColumnInfo(name = "date") var dateSale:String,
    @ColumnInfo(name = "state") var state:Byte = 0
)
// Valores para state
// 0: Estado inicial
// 1: Venta finalizada
// 2: Venta anulada

fun Sale.toEntity() = SaleEntity(
    id = id,
    dateSale = dateSale,
    state = state
)
