package com.example.navigationmenuudemy.domain.model

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.example.navigationmenuudemy.data.database.entities.CategoryEntity

data class Category(
    val name:String,
)

fun CategoryEntity.toDomain() = Category(
    name = name
)
