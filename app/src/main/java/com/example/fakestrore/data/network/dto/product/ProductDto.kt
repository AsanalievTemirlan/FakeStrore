package com.example.fakestrore.data.network.dto.product


import com.example.fakestrore.domain.model.product.ProductModelItem
import com.example.fakestrore.domain.model.product.RatingModel
import com.google.gson.annotations.SerializedName

data class ProductDto(
    @SerializedName("category")
    val category: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String,
    @SerializedName("price")
    val price: Double,
    @SerializedName("rating")
    val rating: RatingDto,
    @SerializedName("title")
    val title: String
)

data class RatingDto(
    @SerializedName("count")
    val count: Int,
    @SerializedName("rate")
    val rate: Double
)


fun ProductDto.toModel(): ProductModelItem {
    return ProductModelItem(
        category = this.category,
        description = this.description,
        id = this.id,
        image = this.image,
        price = this.price,
        ratingModel = this.rating.toModel(),
        title = this.title
    )
}

fun RatingDto.toModel(): RatingModel {
    return RatingModel(
        count = this.count,
        rate = this.rate
    )
}

