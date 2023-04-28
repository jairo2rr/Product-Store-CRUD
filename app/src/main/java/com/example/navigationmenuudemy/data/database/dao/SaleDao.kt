package com.example.navigationmenuudemy.data.database.dao

import androidx.room.*
import com.example.navigationmenuudemy.data.database.entities.SaleEntity
import com.example.navigationmenuudemy.data.database.entities.SaleWithDescriptions

@Dao
interface SaleDao {
    @Upsert
    suspend fun upsertSale(sale:SaleEntity)

    @Query("SELECT * FROM sale_table ORDER BY id DESC")
    suspend fun getAllSales():List<SaleEntity>

    @Query("SELECT * FROM sale_table WHERE state=:state")
    suspend fun getSaleXState(state:Int):List<SaleEntity>

    @Transaction
    @Query("SELECT * FROM sale_table WHERE id=:saleId")
    suspend fun getSaleWithDescriptions(saleId:Int):List<SaleWithDescriptions>
}