package com.example.fakestrore.utils

import androidx.compose.runtime.Immutable


sealed interface UiState<out T> {
    // Состояние загрузки
    data object Loading : UiState<Nothing>

    // Успешное состояние с данными
    data class Success<T>(val data: T) : UiState<T>

    // Состояние ошибки
    data class Error(val message: String? = null) : UiState<Nothing>
}