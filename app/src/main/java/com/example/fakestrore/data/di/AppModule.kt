package com.example.fakestrore.data.di

import com.example.fakestrore.data.network.repositoryImpl.CartRepositoryImpl
import com.example.fakestrore.data.network.repositoryImpl.ProductRepositoryImp
import com.example.fakestrore.domain.repository.CartRepository
import com.example.fakestrore.domain.repository.ProductRepository
import com.example.fakestrore.domain.usecase.AddCartUseCase
import com.example.fakestrore.domain.usecase.CleanCartUseCase
import com.example.fakestrore.domain.usecase.DeleteCartUseCase
import com.example.fakestrore.domain.usecase.GetCartUseCase
import com.example.fakestrore.domain.usecase.ProductUseCase
import com.example.fakestrore.presentation.viewmodel.CartViewModel
import com.example.fakestrore.presentation.viewmodel.ProductViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single<ProductRepository> { ProductRepositoryImp(get()) }

    single<CartRepository> { CartRepositoryImpl(get()) }

    factory { ProductUseCase(get(), get()) }
    factory { DeleteCartUseCase(get()) }
    factory { CleanCartUseCase(get()) }
    factory { AddCartUseCase(get()) }
    factory { GetCartUseCase(get()) }

    viewModel { ProductViewModel(get(), get()) }
    viewModel { CartViewModel(get(), get(), get()) }
}