package com.example.navigationmenuudemy.data.database.dao

import androidx.room.*
import com.example.navigationmenuudemy.data.database.entities.CategoryEntity

@Dao
interface CategoryDao {
    @Query("SELECT * FROM category_table ORDER BY name ASC")
    suspend fun getAllCategories():List<CategoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category:CategoryEntity)

    @Delete
    suspend fun deleteCategory(category:CategoryEntity)

}