package com.example.fakestrore.data.network.api

import com.example.fakestrore.data.network.dto.product.ProductDto
import retrofit2.Response
import retrofit2.http.GET

interface FakeStoreApiService {

    @GET("products")
    suspend fun getProducts(): Response<List<ProductDto>>

}