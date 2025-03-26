package com.example.fakestrore.data.di

import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.fakestrore.data.local.db.FakeStoreDataBase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val localModule = module{

    single {
        Room.databaseBuilder(
            androidApplication(), FakeStoreDataBase::class.java, "fake_store_database"
        ).fallbackToDestructiveMigration().build()
    }

    single { get<FakeStoreDataBase>().productDao() }


}