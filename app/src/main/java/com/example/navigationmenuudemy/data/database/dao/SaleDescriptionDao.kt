package com.example.navigationmenuudemy.data.database.dao

import androidx.room.*
import com.example.navigationmenuudemy.data.database.entities.ProductWithSaleDescriptions
import com.example.navigationmenuudemy.data.database.entities.SaleDescriptionEntity
import com.example.navigationmenuudemy.domain.model.SaleDescription

@Dao
interface SaleDescriptionDao {

    @Query("SELECT * FROM sale_description_table WHERE id=:saleDescriptionId")
    suspend fun getSaleDescription(saleDescriptionId:Int):SaleDescriptionEntity

    @Upsert
    suspend fun upsertSaleDescription(saleDescription: SaleDescriptionEntity)

    @Delete
    suspend fun deleteSaleDescription(saleDescription: SaleDescriptionEntity)

    @Query("SELECT * FROM sale_description_table WHERE saleId=:saleId AND productId=:productId")
    suspend fun getSaleDescriptionByProductId(productId:Int,saleId:Int):List<SaleDescriptionEntity>


}