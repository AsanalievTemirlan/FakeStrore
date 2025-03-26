package com.example.fakestrore.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
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
                Modifier.fillMaxSize().padding(16.dp)
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
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(){
            AsyncImage(
                model = product.image,
                contentDescription = product.description,
                modifier = Modifier
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(R.drawable.ic_logo),
                error = painterResource(R.drawable.ic_logo)
            )
            Box(){
                Text(
                    text = product.title,
                    modifier = Modifier.padding(16.dp),
                    fontSize = 16.sp
                )
            }
        }
    }
}