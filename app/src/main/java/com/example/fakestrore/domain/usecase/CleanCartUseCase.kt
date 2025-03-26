package com.example.fakestrore.domain.usecase

import com.example.fakestrore.domain.repository.CartRepository

class CleanCartUseCase(
    private val cartRepository: CartRepository
) {
    suspend operator fun invoke(){
        cartRepository.clearCart()
    }
}