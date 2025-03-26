package com.example.fakestrore.app

import android.app.Application
import com.example.fakestrore.data.di.appModule
import com.example.fakestrore.data.di.localModule
import com.example.fakestrore.data.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class FakeStoreApp: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidContext(this@FakeStoreApp)
            modules(appModule, networkModule, localModule)
        }
    }

}