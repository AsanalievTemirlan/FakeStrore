package com.example.fakestrore.domain.repository

import com.example.fakestrore.domain.model.product.ProductModelItem

interface CartRepository {
    suspend fun addProductToCart(product: ProductModelItem)
    suspend fun removeProductFromCart(productId: Int)
    suspend fun getCart(): List<ProductModelItem>
    suspend fun clearCart()
}