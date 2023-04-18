package com.example.navigationmenuudemy.data.database.dao

import androidx.room.*
import com.example.navigationmenuudemy.data.database.entities.SaleDescriptionEntity
import com.example.navigationmenuudemy.domain.model.SaleDescription

@Dao
interface SaleDescriptionDao {

    @Query("SELECT * FROM sale_description_table WHERE saleId=:saleId")
    suspend fun getDescriptionsForSale(saleId:Int):List<SaleDescriptionEntity>

    @Transaction
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertSaleDescription(saleDescription: SaleDescriptionEntity)

    @Delete
    suspend fun deleteSaleDescription(saleDescription: SaleDescriptionEntity)

    @Update
    suspend fun updateSaleDescription(saleDescription: SaleDescriptionEntity)
}