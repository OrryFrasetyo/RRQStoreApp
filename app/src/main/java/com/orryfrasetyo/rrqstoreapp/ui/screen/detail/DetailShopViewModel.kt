package com.orryfrasetyo.rrqstoreapp.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orryfrasetyo.rrqstoreapp.data.ShopRepository
import com.orryfrasetyo.rrqstoreapp.model.OrderShop
import com.orryfrasetyo.rrqstoreapp.model.Shop
import com.orryfrasetyo.rrqstoreapp.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailShopViewModel(
    private val repository: ShopRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<OrderShop>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<OrderShop>>
        get() = _uiState

    fun getShopById(shopId: Long) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getOrderShopById(shopId))
        }
    }

    fun addToCart(shop: Shop, count: Int) {
        viewModelScope.launch {
            repository.updateOrderShop(shop.id, count)
        }
    }

}














