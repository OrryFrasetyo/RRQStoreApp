package com.orryfrasetyo.rrqstoreapp.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.orryfrasetyo.rrqstoreapp.R
import com.orryfrasetyo.rrqstoreapp.di.Injection
import com.orryfrasetyo.rrqstoreapp.model.OrderShop
import com.orryfrasetyo.rrqstoreapp.ui.ViewModelFactory
import com.orryfrasetyo.rrqstoreapp.ui.common.UiState
import com.orryfrasetyo.rrqstoreapp.ui.components.ShopItem

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (Long) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()

    when (val currentState = uiState) {
        is UiState.Loading -> {
            LaunchedEffect(viewModel) {
                viewModel.getAllShops()
            }
        }
        is UiState.Success -> {
            HomeContent(
                orderShop = currentState.data,
                modifier = modifier,
                navigateToDetail = navigateToDetail
            )
        }
        is UiState.Error -> {

        }
    }

    LazyColumn(
        contentPadding = PaddingValues(bottom = 80.dp)
    ) {
        item { 
            Search(
                query = viewModel.query.value,
                onQueryChange = viewModel::search,
                modifier = Modifier.background(MaterialTheme.colorScheme.primary)
            )
        }
    }
}

@Composable
fun HomeContent(
    orderShop: List<OrderShop>,
    modifier: Modifier = Modifier,
    navigateToDetail: (Long) -> Unit,
) {
   LazyVerticalGrid(
       columns = GridCells.Adaptive(150.dp),
       contentPadding = PaddingValues(16.dp, top = 100.dp),
       horizontalArrangement = Arrangement.spacedBy(16.dp),
       verticalArrangement = Arrangement.spacedBy(16.dp),
       modifier = modifier
           .testTag("ShopList")
           .fillMaxSize()
   ) {
       items(orderShop) { data ->
           ShopItem(
               image = data.shop.image,
               title = data.shop.title,
               price = data.shop.price,
               modifier = Modifier.clickable {
                   navigateToDetail(data.shop.id)
               }
           )
       }
   } 
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Search(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    SearchBar(
        query = query,
        onQueryChange = onQueryChange,
        onSearch = {},
        active = false,
        onActiveChange = {},
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        placeholder = {
            Text(stringResource(R.string.search_shop))
        },
        shape = MaterialTheme.shapes.large,
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .heightIn(min = 48.dp)
    ) {
    }
}












