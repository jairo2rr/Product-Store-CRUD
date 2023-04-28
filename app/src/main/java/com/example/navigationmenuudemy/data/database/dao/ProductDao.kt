package com.example.navigationmenuudemy.data.database.dao

import androidx.room.*
import com.example.navigationmenuudemy.data.database.entities.CategoryWithProducts
import com.example.navigationmenuudemy.data.database.entities.ProductEntity
import com.example.navigationmenuudemy.data.database.entities.ProductWithSaleDescriptions
import com.example.navigationmenuudemy.data.database.entities.TopSellingProduct

@Dao
interface ProductDao {

    @Query("SELECT * FROM product_table ORDER BY id DESC")
    suspend fun getAllProducts():List<ProductEntity>

    @Upsert
    suspend fun upsertProduct(product:ProductEntity)

    @Query("SELECT * FROM product_table WHERE id=:productId")
    suspend fun findProductFromId(productId:Int):List<ProductEntity>

    @Delete
    suspend fun deleteProduct(product: ProductEntity)

    @Transaction
    @Query("SELECT * FROM product_table WHERE id=:productId")
    suspend fun getProductWithSaleDescriptions(productId: Int):List<ProductWithSaleDescriptions>

    @Query("SELECT productId, SUM(quantity) as totalQuantity FROM sale_description_table GROUP BY productId ORDER BY totalQuantity DESC")
    suspend fun getTopSellingProducts():List<TopSellingProduct>
}