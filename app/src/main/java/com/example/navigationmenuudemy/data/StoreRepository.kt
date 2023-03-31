package com.example.navigationmenuudemy.data

import android.util.Log
import com.example.navigationmenuudemy.data.database.dao.CategoryDao
import com.example.navigationmenuudemy.data.database.dao.ProductDao
import com.example.navigationmenuudemy.data.database.entities.CategoryEntity
import com.example.navigationmenuudemy.data.database.entities.ProductEntity
import com.example.navigationmenuudemy.data.database.entities.toEntity
import com.example.navigationmenuudemy.domain.model.Category
import com.example.navigationmenuudemy.domain.model.Product
import com.example.navigationmenuudemy.domain.model.toDomain
import javax.inject.Inject

class StoreRepository @Inject constructor(
    private val categoryDao: CategoryDao,
    private val productDao: ProductDao
){
    suspend fun getAllProductsDB(): List<Product> {
        val response = productDao.getAllProducts()
        return response.map { it.toDomain() }
    }

    suspend fun getProductsForCategoryDB(categoryId:Int): List<Product> {
        val response = productDao.findProductsFromCategory(categoryId)
        return response.map { it.toDomain() }
    }

    suspend fun getAllCategoriesDB():List<Category>{
        val response = categoryDao.getAllCategories()
        return response.map { it.toDomain() }
    }

    suspend fun insertCategory(category: Category){
        categoryDao.insertCategory(category.toEntity())
    }

    suspend fun insertProduct(product:Product){
        productDao.insertProduct(product.toEntity())
    }

    suspend fun getCategoryXNameDB(name:String):List<Category>{
        val response = categoryDao.findCategoryXName(name)
        return response.map { it.toDomain() }
    }

    suspend fun updateCategory(category: Category){
        categoryDao.updateCategory(category.toEntity())
    }

    suspend fun deleteCategory(category: Category){
        categoryDao.deleteCategory(category.toEntity())
    }

    suspend fun findProductFromId(productId:Int):List<Product>{
        val response = productDao.findProductFromId(productId)
        return response.map { it.toDomain() }
    }
}