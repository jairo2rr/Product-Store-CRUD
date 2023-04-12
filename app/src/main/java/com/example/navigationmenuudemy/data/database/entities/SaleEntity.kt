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
    @ColumnInfo(name = "date") var dateSale:Date,
    @ColumnInfo(name = "state") var state:Byte
)

fun Sale.toEntity() = SaleEntity(
    id = id,
    dateSale = dateSale,
    state = state
)
