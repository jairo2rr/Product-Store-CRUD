package com.example.navigationmenuudemy.data.database.dao

import androidx.room.*
import com.example.navigationmenuudemy.data.database.entities.CategoryEntity
import com.example.navigationmenuudemy.data.database.entities.CategoryWithProducts
import com.example.navigationmenuudemy.domain.model.Category

@Dao
interface CategoryDao {
    @Query("SELECT * FROM category_table ORDER BY id DESC")
    suspend fun getAllCategories():List<CategoryEntity>

    @Upsert
    suspend fun upsertCategory(category:CategoryEntity)

    @Delete
    suspend fun deleteCategory(category:CategoryEntity)

    @Query("SELECT * FROM category_table WHERE name=:name")
    suspend fun findCategoryXName(name:String):List<CategoryEntity>

    @Query("SELECT * FROM category_table WHERE id=:idCategory")
    suspend fun findCategoryById(idCategory:Int):List<CategoryEntity>

    @Transaction
    @Query("SELECT * FROM category_table WHERE name=:name")
    suspend fun getCategoryWithProducts(name: String):List<CategoryWithProducts>

}