package com.example.navigationmenuudemy.domain.model

import com.example.navigationmenuudemy.data.database.entities.ProductEntity

data class Product(
    var product: String,
    var price: Float = 0.0f,
    var stock: Int = 0,
    val categoryId: Int,
)

fun ProductEntity.toDomain() = Product(
    product = product,
    price = price,
    stock = stock,
    categoryId = categoryId
)