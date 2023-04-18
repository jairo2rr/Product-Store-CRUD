package com.example.navigationmenuudemy.data

import android.util.Log
import androidx.room.Query
import androidx.room.Update
import com.example.navigationmenuudemy.data.database.dao.CategoryDao
import com.example.navigationmenuudemy.data.database.dao.ProductDao
import com.example.navigationmenuudemy.data.database.dao.SaleDao
import com.example.navigationmenuudemy.data.database.dao.SaleDescriptionDao
import com.example.navigationmenuudemy.data.database.entities.CategoryEntity
import com.example.navigationmenuudemy.data.database.entities.ProductEntity
import com.example.navigationmenuudemy.data.database.entities.SaleEntity
import com.example.navigationmenuudemy.data.database.entities.toEntity
import com.example.navigationmenuudemy.domain.model.*
import javax.inject.Inject

class StoreRepository @Inject constructor(
    private val categoryDao: CategoryDao,
    private val productDao: ProductDao,
    private val saleDao:SaleDao,
    private val saleDescriptionDao: SaleDescriptionDao
){
    //Category
    suspend fun getAllCategoriesDB():List<Category>{
        val response = categoryDao.getAllCategories()
        return response.map { it.toDomain() }
    }

    suspend fun insertCategory(category: Category){
        categoryDao.insertCategory(category.toEntity())
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
    //Product
    suspend fun insertProduct(product:Product){
        productDao.insertProduct(product.toEntity())
    }
    suspend fun getAllProductsDB(): List<Product> {
        val response = productDao.getAllProducts()
        return response.map { it.toDomain() }
    }
    suspend fun updateProduct(product: Product){
        productDao.updateProduct(product.toEntity())
    }
    suspend fun getProductsForCategoryDB(categoryId:Int): List<Product> {
        val response = productDao.findProductsFromCategory(categoryId)
        return response.map { it.toDomain() }
    }

    suspend fun findProductFromId(productId:Int):List<Product>{
        val response = productDao.findProductFromId(productId)
        return response.map { it.toDomain() }
    }

    suspend fun deleteProduct(product: Product){
        productDao.deleteProduct(product.toEntity())
    }

    //Sale
    suspend fun insertSale(sale:Sale){
        saleDao.insertSale(sale.toEntity())
    }

    suspend fun getAllSales():List<Sale>{
        val response = saleDao.getAllSales()
        return response.map { it.toDomain() }
    }

    suspend fun updateSale(sale:Sale){
        saleDao.updateSale(sale.toEntity())
    }

    suspend fun getSaleXState(state:Int):List<Sale>{
        val response = saleDao.getSaleXState(state)
        return response.map { it.toDomain() }
    }

    //SaleDescription
    suspend fun insertSaleDescription(saleDescription: SaleDescription){
        saleDescriptionDao.insertSaleDescription(saleDescription.toEntity())
    }

    suspend fun getDescriptionsForSale(saleId:Int):List<SaleDescription>{
        val response = saleDescriptionDao.getDescriptionsForSale(saleId)
        return response.map { it.toDomain() }
    }

    suspend fun deleteSaleDescription(saleDescription: SaleDescription){
        saleDescriptionDao.deleteSaleDescription(saleDescription.toEntity())
    }

    suspend fun updateSaleDescription(saleDescription: SaleDescription){
        saleDescriptionDao.updateSaleDescription(saleDescription.toEntity())
    }
}