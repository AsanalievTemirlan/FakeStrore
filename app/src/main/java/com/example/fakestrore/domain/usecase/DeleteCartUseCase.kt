package com.example.fakestrore.domain.usecase

import com.example.fakestrore.domain.repository.CartRepository

class DeleteCartUseCase(
    private val cartRepository: CartRepository
) {
    suspend operator fun invoke(cartId: Int) {
        cartRepository.removeProductFromCart(cartId)
    }
}