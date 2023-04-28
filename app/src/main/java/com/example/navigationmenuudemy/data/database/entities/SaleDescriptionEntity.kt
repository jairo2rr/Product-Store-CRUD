package com.example.navigationmenuudemy.data.database.entities

import androidx.room.*
import com.example.navigationmenuudemy.domain.model.Sale
import com.example.navigationmenuudemy.domain.model.SaleDescription

@Entity(tableName = "sale_description_table",foreignKeys = [ForeignKey(entity = ProductEntity::class, parentColumns = ["id"], childColumns = [ "productId" ], onDelete = ForeignKey.SET_NULL),ForeignKey(entity = SaleEntity::class, parentColumns = ["id"], childColumns = [ "saleId" ], onDelete = ForeignKey.CASCADE)])
data class SaleDescriptionEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id:Int =0,
    @ColumnInfo(name = "quantity") var quantity:Int,
    @ColumnInfo(name = "discount") var discount:Float=0.0f,
    @ColumnInfo(name = "saleId") val saleId:Int,
    @ColumnInfo(name = "productId") val productId:Int
)

data class SaleWithDescriptions(
    @Embedded val sale: SaleEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "saleId"
    )
    val saleDescriptions:List<SaleDescriptionEntity>
)

data class ProductWithSaleDescriptions(
    @Embedded val sale: ProductEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "productId"
    )
    val saleDescriptions:List<SaleDescriptionEntity>
)

fun SaleDescription.toEntity() = SaleDescriptionEntity(
    id=id,
    quantity=quantity,
    discount=discount,
    saleId=saleId,
    productId=productId
)