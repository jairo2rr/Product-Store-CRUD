package com.example.navigationmenuudemy.data.database.dao

import androidx.room.*
import com.example.navigationmenuudemy.data.database.entities.ProductEntity

@Dao
interface ProductDao {

    @Query("SELECT * FROM product_table")
    suspend fun getAllProducts():List<ProductEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product:ProductEntity)

    @Update
    suspend fun updateProduct(product:ProductEntity)

    @Query("SELECT * FROM product_table where categoryId=:categoryId")
    suspend fun findProductsFromCategory(categoryId:Int):List<ProductEntity>

    @Query("SELECT * FROM product_table where id=:productId")
    suspend fun findProductFromId(productId:Int):List<ProductEntity>

    @Delete
    suspend fun deleteProduct(product: ProductEntity)
}