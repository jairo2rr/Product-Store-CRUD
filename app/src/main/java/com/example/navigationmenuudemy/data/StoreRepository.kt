package com.example.navigationmenuudemy.data

import android.util.Log
import androidx.room.Query
import androidx.room.Update
import com.example.navigationmenuudemy.data.database.dao.CategoryDao
import com.example.navigationmenuudemy.data.database.dao.ProductDao
import com.example.navigationmenuudemy.data.database.dao.SaleDao
import com.example.navigationmenuudemy.data.database.dao.SaleDescriptionDao
import com.example.navigationmenuudemy.data.database.entities.*
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

    suspend fun upsertCategory(category: Category){
        categoryDao.upsertCategory(category.toEntity())
    }


    suspend fun getCategoryXNameDB(name:String):List<Category>{
        val response = categoryDao.findCategoryXName(name)
        return response.map { it.toDomain() }
    }

    suspend fun deleteCategory(category: Category){
        categoryDao.deleteCategory(category.toEntity())
    }
    //Product
    suspend fun upsertProduct(product:Product){
        productDao.upsertProduct(product.toEntity())
    }
    suspend fun getAllProductsDB(): List<Product> {
        val response = productDao.getAllProducts()
        return response.map { it.toDomain() }
    }
    suspend fun getProductsWithSaleDescriptions(productId:Int): List<ProductWithSaleDescriptions> {
        return productDao.getProductWithSaleDescriptions(productId)
    }

    suspend fun findProductFromId(productId:Int):List<Product>{
        val response = productDao.findProductFromId(productId)
        return response.map { it.toDomain() }
    }

    suspend fun deleteProduct(product: Product){
        productDao.deleteProduct(product.toEntity())
    }

    suspend fun getTopSellingProducts():List<TopSellingProduct>{
        return productDao.getTopSellingProducts()
    }

    //Sale
    suspend fun upsertSale(sale:Sale){
        saleDao.upsertSale(sale.toEntity())
    }

    suspend fun getAllSales():List<Sale>{
        val response = saleDao.getAllSales()
        return response.map { it.toDomain() }
    }

    suspend fun getSaleXState(state:Int):List<Sale>{
        val response = saleDao.getSaleXState(state)
        return response.map { it.toDomain() }
    }

    suspend fun getSaleWithDescriptions(saleId:Int):List<SaleWithDescriptions>{
        return saleDao.getSaleWithDescriptions(saleId)
    }

    suspend fun getCountSales(state: Int = 1):Int{
        return saleDao.getCountSales(state)
    }

    //SaleDescription
    suspend fun upsertSaleDescription(saleDescription: SaleDescription){
        saleDescriptionDao.upsertSaleDescription(saleDescription.toEntity())
    }

    suspend fun getSaleDescription(saleDescriptionId:Int):SaleDescription{
        val response = saleDescriptionDao.getSaleDescription(saleDescriptionId)
        return response.toDomain()
    }

    suspend fun deleteSaleDescription(saleDescription: SaleDescription){
        saleDescriptionDao.deleteSaleDescription(saleDescription.toEntity())
    }

    suspend fun getSaleDescriptionByProductId(productId: Int,saleId:Int):List<SaleDescription>{
        return saleDescriptionDao.getSaleDescriptionByProductId(productId,saleId).map { it.toDomain() }
    }
}