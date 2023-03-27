package com.example.navigationmenuudemy.domain.model

import com.example.navigationmenuudemy.data.database.entities.CategoryEntity

data class Category(
    val id:Int = 0,
    var name:String,
)

fun CategoryEntity.toDomain() = Category(
    id = id,
    name = name
)
