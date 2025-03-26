package com.example.fakestrore.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.fakestrore.data.local.dao.FakeStoreDao
import com.example.fakestrore.data.local.entity.ProductEntity

@Database(entities = [ProductEntity::class], version = 1)
abstract class FakeStoreDataBase: RoomDatabase() {
    abstract fun productDao(): FakeStoreDao
}