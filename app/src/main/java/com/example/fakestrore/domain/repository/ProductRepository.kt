package com.example.fakestrore.domain.repository

import com.example.fakestrore.domain.model.product.ProductModelItem

interface ProductRepository {

    suspend fun getAllProducts(): Result<List<ProductModelItem>>

}