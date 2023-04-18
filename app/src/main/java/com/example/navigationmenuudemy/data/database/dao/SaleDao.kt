package com.example.navigationmenuudemy.data.database.dao

import androidx.room.*
import com.example.navigationmenuudemy.data.database.entities.SaleEntity

@Dao
interface SaleDao {
    @Transaction
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertSale(sale:SaleEntity)

    @Query("SELECT * FROM sale_table ORDER BY id DESC")
    suspend fun getAllSales():List<SaleEntity>

    @Update
    suspend fun updateSale(sale: SaleEntity)

    @Query("SELECT * FROM sale_table WHERE state=:state")
    suspend fun getSaleXState(state:Int):List<SaleEntity>

}