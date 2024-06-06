package com.vosaa.majoritytechassignment.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.vosaa.majoritytechassignment.data.datastore.CountrySerializer
import com.vosaa.majoritytechassignment.domain.models.ResponseCountry
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Singleton
    @Provides
    fun provideDQDataStore(@ApplicationContext appContext: Context): DataStore<ResponseCountry> {
        return DataStoreFactory.create(
            serializer = CountrySerializer,
            produceFile = { appContext.dataStoreFile("country.json") },
            corruptionHandler = null
        )
    }

}