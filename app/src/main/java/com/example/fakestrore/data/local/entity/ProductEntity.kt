package com.example.fakestrore.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.fakestrore.data.network.dto.product.ProductDto
import com.example.fakestrore.domain.model.product.ProductModelItem
import com.example.fakestrore.domain.model.product.RatingModel

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val category: String,
    val description: String,
    val image: String,
    val price: Double,
    val title: String,
    val ratingCount: Int,
    val ratingRate: Double
)

fun ProductModelItem.toEntity(): ProductEntity {
    return ProductEntity(
        id = this.id,
        category = this.category,
        description = this.description,
        image = this.image,
        price = this.price,
        title = this.title,
        ratingCount = this.ratingModel.count,
        ratingRate = this.ratingModel.rate
    )
}

fun ProductEntity.toModel(): ProductModelItem{
    return ProductModelItem(
        id = this.id,
        category = this.category,
        description = this.description,
        image = this.image,
        price = this.price,
        title = this.title,
        ratingModel = RatingModel(
            count = this.ratingCount,
            rate = this.ratingRate
        )
    )
}

