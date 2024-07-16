package com.orryfrasetyo.rrqstoreapp.ui.screen.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orryfrasetyo.rrqstoreapp.data.ShopRepository
import com.orryfrasetyo.rrqstoreapp.model.OrderShop
import com.orryfrasetyo.rrqstoreapp.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: ShopRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<OrderShop>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<OrderShop>>>
        get() = _uiState

    val groupedShops: StateFlow<Map<Char, List<OrderShop>>> = repository.groupedShops
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyMap())

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query

    fun search(newQuery: String) {
        _query.value = newQuery
        _uiState.value = UiState.Success(repository.searchShops(_query.value))
    }

    fun getAllShops() {
        viewModelScope.launch {
            repository.getAllShops()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { orderShops ->
                    _uiState.value = UiState.Success(orderShops)
                }
        }
    }
}












