package com.example.navigationmenuudemy.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.navigationmenuudemy.data.database.dao.CategoryDao
import com.example.navigationmenuudemy.data.database.dao.ProductDao
import com.example.navigationmenuudemy.data.database.entities.CategoryEntity
import com.example.navigationmenuudemy.data.database.entities.ProductEntity

@Database(entities = [CategoryEntity::class, ProductEntity::class],version = 1)
abstract class StoreDatabase:RoomDatabase() {
    abstract fun getCategoryDao():CategoryDao
    abstract fun getProductDao():ProductDao
}