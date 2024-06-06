package com.vosaa.majoritytechassignment.di

import com.vosaa.majoritytechassignment.presentation.country.CountryAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AdapterModule {

    @Singleton
    @Provides
    fun provideHomeAdapter(): CountryAdapter {
        return CountryAdapter()
    }
}