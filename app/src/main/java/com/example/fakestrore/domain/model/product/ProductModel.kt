package com.example.fakestrore.domain.model.product


import com.google.gson.annotations.SerializedName



data class ProductModelItem(
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
    val ratingModel: RatingModel,
    @SerializedName("title")
    val title: String
)

data class RatingModel(
    @SerializedName("count")
    val count: Int,
    @SerializedName("rate")
    val rate: Double
)
