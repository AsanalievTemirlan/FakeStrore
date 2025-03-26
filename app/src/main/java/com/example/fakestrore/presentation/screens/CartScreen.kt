package com.example.fakestrore.presentation.screens

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import com.example.fakestrore.presentation.viewmodel.CartViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun CartScreen(navController: NavController) {

    val viewModel: CartViewModel = koinViewModel()
    val cartProduct by viewModel.getCart.collectAsState()
    Log.e("ololo", "CartScreen: $cartProduct", )
}