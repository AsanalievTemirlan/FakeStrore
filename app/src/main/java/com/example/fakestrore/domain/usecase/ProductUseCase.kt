package com.example.fakestrore.domain.usecase

import com.example.fakestrore.data.local.dao.FakeStoreDao
import com.example.fakestrore.data.local.entity.toEntity
import com.example.fakestrore.data.local.entity.toModel
import com.example.fakestrore.domain.model.product.ProductModelItem
import com.example.fakestrore.domain.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProductUseCase(
    private val productRepository: ProductRepository,
    private val dao: FakeStoreDao
) {

    suspend operator fun invoke(): Result<List<ProductModelItem>> = withContext(Dispatchers.IO) {
        return@withContext try {
            val localData = dao.getAllProducts()

            if (localData.isNotEmpty()) {
                Result.success(localData.map { it.toModel() })
            } else {
                val result = productRepository.getAllProducts()

                result.onSuccess { list ->
                    dao.insertAll(list.map { it.toEntity() }) // Кэшируем
                }

                result // Возвращаем результат API (если успех) или ошибку
            }
        } catch (e: Exception) {
            Result.failure(e) // Если ошибка при доступе к БД
        }
    }
}
