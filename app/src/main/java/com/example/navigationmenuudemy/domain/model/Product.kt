package com.example.navigationmenuudemy.domain.model

import com.example.navigationmenuudemy.data.database.entities.ProductEntity

data class Product(
    val id:Int = 0,
    var product: String,
    var price: Float = 0.0f,
    var stock: Int = 0,
    var uri:String,
    var favorite:Boolean=false,
    val categoryId: Int
)

fun ProductEntity.toDomain() = Product(
    id = id,
    product = product,
    price = price,
    stock = stock,
    uri=uri,
    favorite=favorite,
    categoryId = categoryId
)