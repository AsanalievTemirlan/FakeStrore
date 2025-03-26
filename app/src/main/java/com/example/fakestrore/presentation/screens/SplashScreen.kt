package com.example.fakestrore.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fakestrore.R
import com.example.fakestrore.presentation.navigation.Route
import com.example.fakestrore.presentation.ui.theme.robotoFontFamily
import com.example.fakestrore.presentation.viewmodel.ProductViewModel
import com.example.fakestrore.utils.UiState
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel

@Composable
fun SplashScreen(navController: NavController) {

    val viewModel: ProductViewModel = koinViewModel()
    val response by viewModel.products.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),
        contentAlignment = Alignment.Center
    ) {
        DmarketText(
            textSize = 48.sp,
            iconH = 42.dp,
            iconW = 56.dp
        )
    }

    when (response) {
        is UiState.Loading -> {}

        is UiState.Success -> NavigateTo(navController)

        is UiState.Error -> NavigateTo(navController)

    }

}

@Composable
private fun NavigateTo(navController: NavController) {
    LaunchedEffect(Unit) {
        delay(2000)
        navController.navigate(Route.PRODUCT)
    }
}

@Composable
fun DmarketText(
    textSize: TextUnit = 28.sp,
    iconH: Dp = 32.dp,
    iconW: Dp = 24.dp
) {


    Row {
        Icon(
            painter = painterResource(id = R.drawable.ic_logo),
            contentDescription = null,
            modifier = Modifier.size(width = iconW, height = iconH)
        )
        Text(
            text = "market",
            style = TextStyle(
                fontSize = textSize,
                fontFamily = robotoFontFamily
            )
        )
    }

}