package com.example.fakestrore.presentation.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.fakestrore.presentation.viewmodel.CartViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun CartScreen(navController: NavController) {

    val viewModel: CartViewModel = koinViewModel()

}