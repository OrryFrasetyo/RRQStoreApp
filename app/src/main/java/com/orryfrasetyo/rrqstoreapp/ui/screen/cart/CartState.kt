package com.orryfrasetyo.rrqstoreapp.ui.screen.cart

import com.orryfrasetyo.rrqstoreapp.model.OrderShop

data class CartState(
    val orderShop: List<OrderShop>,
    val totalPrice: Double
)