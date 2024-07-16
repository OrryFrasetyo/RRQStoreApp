package com.orryfrasetyo.rrqstoreapp.ui.screen.cart

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.orryfrasetyo.rrqstoreapp.R
import com.orryfrasetyo.rrqstoreapp.di.Injection
import com.orryfrasetyo.rrqstoreapp.ui.ViewModelFactory
import com.orryfrasetyo.rrqstoreapp.ui.common.UiState
import com.orryfrasetyo.rrqstoreapp.ui.components.CartItem
import com.orryfrasetyo.rrqstoreapp.ui.components.OrderButton

@Composable
fun CartScreen(
    viewModel: CartViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    ),
    onOrderButtonClicked: (String) -> Unit,
    navigateToDetail: (Long) -> Unit,
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getAddedOrderShops()
            }
            is UiState.Success -> {
                CartContent(
                    uiState.data,
                    onProductCountChanged = { shopId, count ->
                        viewModel.updateOrderShop(shopId, count)
                    },
                    onOrderButtonClicked = onOrderButtonClicked,
                    navigateToDetail = navigateToDetail
                )
            }
            is UiState.Error -> {

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartContent(
    state: CartState,
    onProductCountChanged: (id: Long, count: Int) -> Unit,
    onOrderButtonClicked: (String) -> Unit,
    modifier: Modifier = Modifier,
    navigateToDetail: (Long) -> Unit
) {
    val shareMessage = stringResource(
        R.string.share_message,
        state.orderShop.count(),
        state.totalPrice
    )
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.weight(weight = 1f)
        ) {
            items(state.orderShop, key = { it.shop.id} ) { item ->
                CartItem(
                    shopId = item.shop.id,
                    image = item.shop.image,
                    title = item.shop.title,
                    totalPrice = item.shop.price * item.count,
                    count = item.count,
                    onProductCountChanged = onProductCountChanged,
                    modifier = Modifier.clickable {
                        navigateToDetail(item.shop.id)
                    }
                )
                Divider()
            }
        }
        OrderButton(
            text = stringResource(R.string.total_order, state.totalPrice),
            enabled = state.orderShop.isNotEmpty(),
            onClick = {
                onOrderButtonClicked(shareMessage)
            },
            modifier = Modifier.padding(16.dp)
        )
    }
}










