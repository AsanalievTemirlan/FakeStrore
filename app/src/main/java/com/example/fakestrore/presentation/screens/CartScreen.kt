package com.example.fakestrore.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.fakestrore.R
import com.example.fakestrore.domain.model.product.ProductModelItem
import com.example.fakestrore.presentation.ui.theme.GrayDark
import com.example.fakestrore.presentation.viewmodel.CartViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(navController: NavController) {

    val viewModel: CartViewModel = koinViewModel()
    val cartProduct by viewModel.getCart.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { DmarketText() },
                actions = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                })
        },
        content = {
            Box(
                Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                if(cartProduct.isEmpty()){
                    Text(
                        text = "Empty",
                        fontSize = 24.sp,
                        modifier = Modifier.align(Alignment.Center),
                        color = GrayDark
                    )
                }
                Column(
                    modifier = Modifier.padding(it)
                ) {
                    Text(
                        text = "Cart",
                        fontSize = 32.sp
                    )
                    LazyColumn() {
                        items(cartProduct.size) {
                            CartCard(
                                delete = { viewModel.deleteCart(cartProduct[it].id) },
                                product = cartProduct[it]
                            )
                        }
                    }
                }
            }
        },
        floatingActionButton = {

        }

    )

}

@Composable
fun CartCard(
    delete:() -> Unit,
    product: ProductModelItem,
) {
    Card(
        modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(){
            AsyncImage(
                model = product.image,
                contentDescription = product.description,
                modifier = Modifier
                    .padding(16.dp)
                    .width(174.dp),
                contentScale = ContentScale.Fit,
                placeholder = painterResource(R.drawable.ic_logo),
                error = painterResource(R.drawable.ic_logo)
            )
            VerticalDivider()

            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = product.title,
                    modifier = Modifier
                        .padding(start = 16.dp, top = 16.dp, end = 16.dp)
                        // Добавляем нижний отступ, чтобы текст не заходил под Row
                        .padding(bottom = 60.dp)
                        .align(Alignment.TopStart),
                    fontSize = 16.sp,
                    maxLines = 3, // Ограничиваем число строк (по необходимости)
                    overflow = TextOverflow.Ellipsis
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .align(Alignment.BottomCenter)
                ) {
                    Text(text = "${product.price}$", fontSize = 20.sp)
                    IconButton(onClick = delete) {
                        Icon(
                            painter = painterResource(R.drawable.ic_shop_cart_outline_remove),
                            contentDescription = "Delete"
                        )
                    }
                }
            }

        }
    }
}