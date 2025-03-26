package com.example.fakestrore.domain.usecase

import com.example.fakestrore.domain.model.product.ProductModelItem
import com.example.fakestrore.domain.repository.CartRepository

class AddCartUseCase(
    private val cartRepository: CartRepository
) {
    suspend operator fun invoke(product: ProductModelItem){
        cartRepository.addProductToCart(product)
    }
}