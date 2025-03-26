package com.example.fakestrore.presentation.screens

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil3.compose.AsyncImage
import com.example.fakestrore.R
import com.example.fakestrore.domain.model.product.ProductModelItem
import com.example.fakestrore.presentation.ui.theme.GrayDark
import com.example.fakestrore.presentation.ui.theme.GrayMiddle
import com.example.fakestrore.presentation.ui.theme.White
import com.example.fakestrore.presentation.ui.theme.sfProFontFamily

@Composable
fun ProductList(products: List<ProductModelItem>, lazyListState: LazyListState, onClick: (ProductModelItem) -> Unit) {
    LazyColumn(
        state = lazyListState,
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(top = 100.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp) // Уменьшаем вертикальный отступ между строками
    ) {
        items(products.chunked(2)) { productRow ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp) // Уменьшаем горизонтальный отступ между карточками
            ) {
                productRow.forEach { product ->
                    ProductItem(
                        product = product,
                        onClick = {
                            onClick(it)
                        } ,
                        modifier = Modifier.weight(1f) // Карточки равномерно распределяют ширину
                    )
                }
                if (productRow.size == 1) {
                    Spacer(modifier = Modifier.weight(1f)) // Добавляем Spacer для выравнивания
                }
            }
        }
    }
}

@Composable
fun ProductItem(product: ProductModelItem, modifier: Modifier = Modifier, onClick: (ProductModelItem) -> Unit) {
    Card(
        modifier = modifier.clickable {
            onClick(product)
        },
        colors = CardDefaults.cardColors(
            containerColor = White // Явно задаём белый фон для карточки
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = product.image, // URL изображения
                contentDescription = product.description,
                modifier = Modifier
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(R.drawable.ic_logo), // Заглушка на время загрузки
                error = painterResource(R.drawable.ic_logo)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(), // Занимаем всю ширину карточки
                horizontalArrangement = Arrangement.SpaceBetween, // Распределяем элементы по краям
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = product.title,
                    fontSize = 12.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis, // Добавляем многоточие, если текст обрезается
                    modifier = Modifier
                        .weight(1f) // Ограничиваем ширину текста, чтобы он не занимал всё пространство
                        .padding(end = 8.dp), // Добавляем отступ справа
                )
                Text(
                    text = "${product.price}$",
                    fontSize = 16.sp,
                    fontFamily = sfProFontFamily,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}

@Composable
fun CategoryDialog(
    onDismiss: () -> Unit,
    onCategorySelected: (String) -> Unit,
    selected: String
) {
    val categories = listOf("All", "Men's clothing", "Jewelery", "Electronics", "Women's clothing")
    var selectedCategory by remember { mutableStateOf(selected) }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            color = Color.White,
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.padding(vertical = 20.dp, horizontal = 16.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_category),
                    contentDescription = "Close",
                    modifier = Modifier
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Categories",
                    fontSize = 24.sp,
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Choose your preferred",
                    fontSize = 14.sp,
                    color = GrayDark
                )
                Spacer(modifier = Modifier.height(24.dp))

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    categories.forEach { category ->
                        CategoryItem(
                            text = category,
                            selected = selectedCategory == category,
                            onClick = {
                                selectedCategory = category
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Decline",
                        fontSize = 16.sp,
                        color = Color.Black,
                        modifier = Modifier
                            .clickable {
                                onDismiss()
                            }
                            .padding(end = 20.dp))

                    Text("Accept",
                        fontSize = 16.sp,
                        color = Color.Black,
                        modifier = Modifier.clickable {
                            onCategorySelected(selectedCategory)
                            onDismiss()
                        })

                }
            }
        }
    }
}


@Composable
fun CategoryItem(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = text,
            color = if(selected) Color.Black else GrayDark
        )
        RadioButton(
            selected = selected,
            onClick = {onClick()},
            colors = RadioButtonDefaults.colors(
                selectedColor = Color.Black,
                unselectedColor = Color.Black
            ),
            modifier = Modifier.size(20.dp)
        )
    }
    HorizontalDivider()

}

@Composable
fun Sp(dp: Dp = 16.dp){
    Spacer(modifier = Modifier.height(dp))
}

@Composable
fun RatingBar(rating: Double, modifier: Modifier = Modifier) {
    Row(modifier = modifier) {
        val fullStars = rating.toInt()
        val hasHalfStar = (rating - fullStars) >= 0.5
        val emptyStars = 5 - fullStars - if (hasHalfStar) 1 else 0

        repeat(fullStars) {
            Icon(
                painter = painterResource(R.drawable.ic_star_fill),
                contentDescription = "Full Star",
                tint = Color.Black
            )
        }

        if (hasHalfStar) {
            Icon(
                painter = painterResource(R.drawable.ic_star_half),
                contentDescription = "Half Star",
                tint = Color.Black
            )
        }

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
        Text(
            text = title,
            color = GrayMiddle,
            fontSize = 16.sp,
            modifier = Modifier.width(80.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        if (value != null) {
            Text(text = value, fontSize = valueFontSize)
        } else {
            content?.invoke()
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