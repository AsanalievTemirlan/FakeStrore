package com.example.fakestrore.presentation.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.fakestrore.R
import com.example.fakestrore.domain.model.product.ProductModelItem
import com.example.fakestrore.presentation.navigation.Route
import com.example.fakestrore.presentation.ui.theme.GrayDark
import com.example.fakestrore.presentation.ui.theme.GrayLight
import com.example.fakestrore.presentation.ui.theme.GrayMiddle
import com.example.fakestrore.presentation.ui.theme.robotoFontFamily
import com.example.fakestrore.presentation.viewmodel.ProductViewModel
import com.example.fakestrore.utils.UiState
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductScreen(navController: NavController) {
    val viewModel: ProductViewModel = koinViewModel()
    val response by viewModel.products.collectAsState()

    val lazyListState = rememberLazyListState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    // Высота области заголовка
    val headerHeight = 100.dp
    val headerHeightPx = with(LocalDensity.current) { headerHeight.toPx() }

    // Прогресс сворачивания на основе прокрутки
    val scrollOffset by derivedStateOf {
        lazyListState.firstVisibleItemScrollOffset.toFloat()
    }
    val collapseProgress by animateFloatAsState(
        targetValue = (scrollOffset / headerHeightPx).coerceIn(0f, 1f),
        animationSpec = tween(durationMillis = 300), label = "collapseProgress"
    )

    var categoryName by remember { mutableStateOf("All") }
    var showDialog by remember { mutableStateOf(false) }

    var showDetail by remember { mutableStateOf(false) }
    var detailId: ProductModelItem? = null


    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                colors = TopAppBarColors(
                    containerColor = GrayLight,
                    scrolledContainerColor = Color.Transparent, // Цвет фона при прокрутке
                    navigationIconContentColor = Color.Black, // Цвет иконки навигации
                    titleContentColor = Color.Black, // Цвет текста заголовка
                    actionIconContentColor = Color.Black // Цвет иконки действия
                ),
                modifier = Modifier
                    .statusBarsPadding(),
                title = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        // Логотип "Dmarket"
                        Box(
                            modifier = Modifier
                                .alpha(1f - collapseProgress)
                                .align(Alignment.Center)
                        ) {
                            DmarketText()
                        }

                        // "Products" и "ALL"
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .fillMaxWidth()
                                .alpha(collapseProgress)
                        ) {
                            Text(
                                text = "Products",
                                fontSize = 24.sp,
                                fontFamily = robotoFontFamily,
                                fontWeight = FontWeight.W400
                            )
                            Text(
                                text = categoryName,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.W400,
                                fontFamily = robotoFontFamily,
                                color = Color.Black
                            )
                        }
                    }
                },
                navigationIcon = {
                    IconButton(onClick = {
                        showDialog = true
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_category),
                            contentDescription = "Menu"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* Открыть корзину */ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_cart_fill),
                            contentDescription = "Cart"
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { paddingValues ->
        // Контент экрана
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            // Заголовок, который "перемещается" в TopAppBar
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(headerHeight)
                    .offset(y = (-collapseProgress * headerHeightPx).dp)
                    .padding(16.dp)
            ) {
                Text(
                    text = "Products",
                    fontSize = 24.sp,
                    modifier = Modifier
                        .alpha(1f - collapseProgress)
                        .padding(top = 0.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = categoryName,
                    fontSize = 16.sp,
                    color = Color.Black,
                    modifier = Modifier.alpha(1f - collapseProgress)
                )
            }
            when (response) {
                is UiState.Success -> {
                    val data = remember {
                        ((response as UiState.Success).data).toMutableStateList()
                    }
                    val categoryData = remember(categoryName, data) {
                        if (categoryName == "All") data
                        else data.filter { it.category == categoryName.lowercase() }
                    }
                    ProductList(
                        products = categoryData,
                        lazyListState = lazyListState,
                        onClick = {
                            showDetail = true
                            detailId = it
                        }
                    )
                }

                is UiState.Loading -> {}
                is UiState.Error -> {}
            }

            if (showDialog) {
                CategoryDialog(
                    onCategorySelected = {
                        categoryName = it
                        showDialog = false
                    },
                    onDismiss = {
                        showDialog = false
                    },
                    selected = categoryName
                )
            }

            if (showDetail) {
                ProductDetail(
                    onDismiss = {
                        showDetail = false
                    },
                    product = detailId!!,
                    addCart = {viewModel.addCart(it)},
                    goToCart = {navController.navigate(Route.CART)}
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetail(
    onDismiss: () -> Unit,
    product: ProductModelItem,
    addCart: (ProductModelItem) -> Unit,
    goToCart: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val addTo = remember { mutableStateOf(true) }

    ModalBottomSheet(
        containerColor = Color.White,
        onDismissRequest = onDismiss,
        sheetState = sheetState,
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.padding(16.dp)
        ) {
            AsyncImage(
                model = product.image, // URL изображения
                contentDescription = product.description,
                modifier = Modifier
                    .fillMaxWidth()
                    .size(height = 300.dp, width = 380.dp),
                contentScale = ContentScale.Fit,
                placeholder = painterResource(R.drawable.ic_logo), // Заглушка на время загрузки
                error = painterResource(R.drawable.ic_logo)
            )
            Sp()
            HorizontalDivider()
            Sp()
            Text(
                text = product.title,
                fontSize = 18.sp,
            )
            Sp()
            InfoRow(title = "Category", value = product.category)
            Spacer(modifier = Modifier.height(8.dp))
            InfoRow(title = "Rating") {
                RatingBar(product.ratingModel.rate)
            }
            Spacer(modifier = Modifier.height(8.dp))
            InfoRow(title = "Price", value = "${product.price}$", valueFontSize = 26.sp)
            Sp()
            Text(
                "Description", color = GrayMiddle,
                fontSize = 16.sp,
            )
            Spacer(modifier = Modifier.height(10.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = product.description,
                    color = GrayDark,
                    fontSize = 12.sp
                )
            }
            Sp()
            if (addTo.value) {
                Button(
                    onClick = {
                        addTo.value = false
                        addCart(product)
                    },
                    border = BorderStroke(1.dp, color = Color.Black),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black
                    ),
                    shape = RoundedCornerShape(360.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Add to cart",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.W700
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Icon(
                            painter = painterResource(R.drawable.ic_shop_cart_outline_add),
                            contentDescription = "ADD TO CART",
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                }
            } else {
                Button(
                    onClick = {
                        goToCart()
                    },
                    border = BorderStroke(1.dp, color = Color.Black),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(360.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                    ) {
                        Text(
                            text = "GO TO CART",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.W700,
                            modifier = Modifier.align(Alignment.CenterStart)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Icon(
                            painter = painterResource(R.drawable.ic_shop_cart_outline_go),
                            contentDescription = "ADD TO CART",
                            modifier = Modifier.align(Alignment.CenterEnd)
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun RatingBar(rating: Double, modifier: Modifier = Modifier) {
    Row(modifier = modifier) {
        val fullStars = rating.toInt()
        val hasHalfStar = (rating - fullStars) >= 0.5
        val emptyStars = 5 - fullStars - if (hasHalfStar) 1 else 0

        // Полные звезды
        repeat(fullStars) {
            Icon(
                painter = painterResource(R.drawable.ic_star_fill),
                contentDescription = "Full Star",
                tint = Color.Black
            )
        }

        // Половинчатая звезда
        if (hasHalfStar) {
            Icon(
                painter = painterResource(R.drawable.ic_star_half),
                contentDescription = "Half Star",
                tint = Color.Black
            )
        }

        // Пустые звезды
        repeat(emptyStars) {
            Icon(
                painter = painterResource(R.drawable.ic_star_empty),
                contentDescription = "Empty Star",
                tint = Color.Black
            )
        }
    }
}

@Composable
fun InfoRow(
    title: String,
    value: String? = null,
    valueFontSize: TextUnit = 16.sp,
    content: @Composable (() -> Unit)? = null
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Заголовок фиксированной ширины
        Text(
            text = title,
            color = GrayMiddle,
            fontSize = 16.sp,
            modifier = Modifier.width(80.dp) // фиксируем ширину
        )

        Spacer(modifier = Modifier.width(8.dp))

        if (value != null) {
            Text(text = value, fontSize = valueFontSize)
        } else {
            content?.invoke()
        }
    }
}

