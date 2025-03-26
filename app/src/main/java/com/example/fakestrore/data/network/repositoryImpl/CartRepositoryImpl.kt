package com.example.fakestrore.data.network.repositoryImpl

import com.example.fakestrore.data.local.dao.FakeStoreDao
import com.example.fakestrore.data.local.entity.toEntity
import com.example.fakestrore.data.local.entity.toModel
import com.example.fakestrore.domain.model.product.ProductModelItem
import com.example.fakestrore.domain.repository.CartRepository

class CartRepositoryImpl(private val dao: FakeStoreDao): CartRepository {
    override suspend fun addProductToCart(product: ProductModelItem) {
        dao.insertCart(product.toEntity())
    }

    override suspend fun removeProductFromCart(productId: Int) {
        dao.deleteById(productId)
    }

    override suspend fun getCart(): List<ProductModelItem> {
        return dao.getCart().map { it.toModel() }
    }

    override suspend fun clearCart() {
        dao.deleteAll()
    }
}