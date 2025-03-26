package com.example.fakestrore.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.fakestrore.data.local.entity.ProductEntity
import retrofit2.http.GET

@Dao
interface FakeStoreDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(products: List<ProductEntity>)

    @Query("SELECT * FROM products")
    fun getAllProducts(): List<ProductEntity>

    @Query("SELECT * FROM products WHERE id = :id")
    suspend fun getProductById(id: Int): ProductEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCart(product: ProductEntity)

    @Query("SELECT * FROM products ")
    suspend fun getCart(): List<ProductEntity>

    @Query("DELETE FROM products WHERE id = :itemId")
    suspend fun deleteById(itemId: Int)

    @Query("DELETE FROM products")
    suspend fun deleteAll()

}