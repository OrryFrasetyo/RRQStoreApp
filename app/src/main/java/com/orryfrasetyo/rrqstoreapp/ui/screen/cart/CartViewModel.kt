package com.orryfrasetyo.rrqstoreapp.ui.screen.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orryfrasetyo.rrqstoreapp.data.ShopRepository
import com.orryfrasetyo.rrqstoreapp.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CartViewModel(
    private val repository: ShopRepository
) : ViewModel(){
    private val _uiState: MutableStateFlow<UiState<CartState>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<CartState>>
        get() = _uiState

    fun getAddedOrderShops() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            repository.getAddedOrderShops()
                .collect { orderShop ->
                    val totalPrice =
                        orderShop.sumOf { it.shop.price * it.count }
                    _uiState.value = UiState.Success(CartState(orderShop, totalPrice))
                }
        }
    }

    fun updateOrderShop(shopId: Long, count: Int) {
        viewModelScope.launch {
            repository.updateOrderShop(shopId, count)
                .collect { isUpdated ->
                    if (isUpdated) {
                        getAddedOrderShops()
                    }
                }
        }
    }
}













