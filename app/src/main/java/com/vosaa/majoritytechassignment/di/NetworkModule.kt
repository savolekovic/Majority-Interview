package com.vosaa.majoritytechassignment.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.vosaa.majoritytechassignment.BuildConfig
import com.vosaa.majoritytechassignment.data.remote.ApiService
import com.vosaa.majoritytechassignment.data.remote.CountryDeserializer
import com.vosaa.majoritytechassignment.domain.models.Country
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val dispatcher = Dispatcher()
        dispatcher.maxRequests = 1
        val builder = OkHttpClient.Builder()
            .connectTimeout(90, TimeUnit.SECONDS)
            .writeTimeout(90, TimeUnit.SECONDS)
            .readTimeout(90, TimeUnit.SECONDS)
            .dispatcher(dispatcher)
        if (BuildConfig.DEBUG)
            builder.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        return builder.build()
    }

    @Singleton
    @Provides
    fun provideApiService(client: OkHttpClient, gson: Gson): ApiService {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
            .create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideGson(
        deserializer: CountryDeserializer,
    ): Gson = Gson()
//        .registerTypeAdapter(Country::class.java, deserializer)
//        .create()
}