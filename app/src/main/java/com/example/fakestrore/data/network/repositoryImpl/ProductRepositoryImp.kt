package com.example.fakestrore.data.network.repositoryImpl

import com.example.fakestrore.data.network.api.FakeStoreApiService
import com.example.fakestrore.data.network.dto.product.toModel
import com.example.fakestrore.domain.model.product.ProductModelItem
import com.example.fakestrore.domain.repository.ProductRepository

class ProductRepositoryImp(
    private val api: FakeStoreApiService
) : ProductRepository {
    override suspend fun getAllProducts(): Result<List<ProductModelItem>> {
        return try {
            val response = api.getProducts()

            if (response.body() != null && response.isSuccessful) {
                Result.success(response.body()!!.map { it.toModel() })
            } else {
                Result.failure(Exception("Error ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}