package com.example.fakestrore.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fakestrore.domain.model.product.ProductModelItem
import com.example.fakestrore.domain.usecase.AddCartUseCase
import com.example.fakestrore.domain.usecase.ProductUseCase
import com.example.fakestrore.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductViewModel(
    private val product: ProductUseCase,
    private val addCartUseCase: AddCartUseCase
) : ViewModel() {

    private val _products = MutableStateFlow<UiState<List<ProductModelItem>>>(UiState.Loading)
    val products = _products.asStateFlow()

    init {
        getProducts()
    }

    private fun getProducts() {
        viewModelScope.launch {
            product().onSuccess {
                _products.value = UiState.Success(it)
            }.onFailure {
                _products.value = UiState.Error(it.toString())
            }
        }
    }

    fun addCart(product: ProductModelItem) {
        viewModelScope.launch {
            addCartUseCase.invoke(product)
        }
    }
}