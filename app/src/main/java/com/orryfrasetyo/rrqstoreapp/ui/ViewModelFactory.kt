package com.orryfrasetyo.rrqstoreapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.orryfrasetyo.rrqstoreapp.data.ShopRepository
import com.orryfrasetyo.rrqstoreapp.ui.screen.cart.CartViewModel
import com.orryfrasetyo.rrqstoreapp.ui.screen.detail.DetailShopViewModel
import com.orryfrasetyo.rrqstoreapp.ui.screen.home.HomeViewModel

class ViewModelFactory(
    private val repository: ShopRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(DetailShopViewModel::class.java)) {
            return DetailShopViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(CartViewModel::class.java)) {
            return CartViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}











