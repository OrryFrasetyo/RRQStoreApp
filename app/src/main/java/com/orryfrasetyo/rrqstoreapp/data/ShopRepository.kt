package com.orryfrasetyo.rrqstoreapp.data

import com.orryfrasetyo.rrqstoreapp.model.FakeShopDataSource
import com.orryfrasetyo.rrqstoreapp.model.OrderShop
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class ShopRepository {
    private val orderShops = mutableListOf<OrderShop>()

    init {
        if (orderShops.isEmpty()) {
            FakeShopDataSource.dummyShops.forEach {
                orderShops.add(OrderShop(it, 0))
            }
        }
    }

    private val _groupedShops: MutableStateFlow<Map<Char, List<OrderShop>>> = MutableStateFlow(emptyMap())
    val groupedShops: StateFlow<Map<Char, List<OrderShop>>> get() = _groupedShops

    fun searchShops(query: String): List<OrderShop> {
        return orderShops.filter {
            it.shop.title.contains(query, ignoreCase = true)
        }
    }

    fun getAllShops(): Flow<List<OrderShop>> {
        return flowOf(orderShops)
    }

    fun getOrderShopById(shopId: Long): OrderShop {
        return orderShops.first {
            it.shop.id == shopId
        }
    }

    fun updateOrderShop(shopId: Long, newCountValue: Int): Flow<Boolean> {
        val index = orderShops.indexOfFirst { it.shop.id == shopId }
        val result = if (index >= 0) {
            val orderShop = orderShops[index]
            orderShops[index] =
                orderShop.copy(shop = orderShop.shop, count = newCountValue)
            true
        } else {
            false
        }
        return flowOf(result)
    }

    fun getAddedOrderShops(): Flow<List<OrderShop>> {
        return getAllShops()
            .map { orderShops ->
                orderShops.filter { orderShop ->
                    orderShop.count != 0
                }
            }
    }

    companion object {
        @Volatile
        private var instance: ShopRepository? = null

        fun getInstance(): ShopRepository =
            instance ?: synchronized(this) {
                ShopRepository().apply {
                    instance = this
                }
            }
    }
}












