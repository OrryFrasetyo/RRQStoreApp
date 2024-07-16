package com.orryfrasetyo.rrqstoreapp.di

import com.orryfrasetyo.rrqstoreapp.data.ShopRepository

object Injection {
    fun provideRepository(): ShopRepository {
        return ShopRepository.getInstance()
    }
}