package com.example.fakestrore.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fakestrore.domain.model.product.ProductModelItem
import com.example.fakestrore.domain.usecase.CleanCartUseCase
import com.example.fakestrore.domain.usecase.DeleteCartUseCase
import com.example.fakestrore.domain.usecase.GetCartUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CartViewModel(
    private val getCartUseCase: GetCartUseCase,
    private val cleanCartUseCase: CleanCartUseCase,
    private val deleteCartUseCase: DeleteCartUseCase
) : ViewModel() {

    private val _getCart = MutableStateFlow<List<ProductModelItem>>(emptyList())
    val getCart = _getCart.asStateFlow()

    init {
        getAllCart()
    }

    private fun getAllCart() {
        viewModelScope.launch {
            _getCart.value = getCartUseCase.invoke()
        }
    }

    fun cleanCart() {
        viewModelScope.launch {
            cleanCartUseCase()
            getAllCart() // обновляем
        }
    }

    fun deleteCart(productId: Int) {
        viewModelScope.launch {
            deleteCartUseCase(productId)
            getAllCart() // обновляем
        }
    }


}