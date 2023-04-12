package com.example.navigationmenuudemy.data.database.entities

import androidx.room.*
import androidx.room.ForeignKey.Companion.CASCADE
import com.example.navigationmenuudemy.domain.model.Product

@Entity(tableName = "product_table", foreignKeys = [ForeignKey(entity = CategoryEntity::class, parentColumns = ["id"], childColumns = [ "categoryId" ], onDelete = CASCADE)])
data class ProductEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id:Int = 0,
    @ColumnInfo(name = "product") var product:String,
    @ColumnInfo(name = "price") var price:Float=0.0f,
    @ColumnInfo(name = "stock") var stock:Int=0,
    @ColumnInfo(name = "uri") var uri:String,
    @ColumnInfo(name = "favorite") var favorite:Boolean=false,
    @ColumnInfo(name = "categoryId") val categoryId:Int
)

data class CategoryWithProducts(
    @Embedded val category:CategoryEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "categoryId"
    )
    val products:List<ProductEntity>
)

fun Product.toEntity() = ProductEntity(
    id=id,
    product = product,
    price = price,
    stock = stock,
    uri=uri,
    favorite=favorite,
    categoryId = categoryId
)
