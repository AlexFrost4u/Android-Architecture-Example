package com.example.android_architecture_example.di

import android.net.Network
import com.example.android_architecture_example.domain.User
import com.example.android_architecture_example.network.NetworkMapper
import com.example.android_architecture_example.network.UserDTO
import com.example.android_architecture_example.network.UserRetrofit
import com.example.android_architecture_example.util.EntityMapper
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Singleton
    @Provides
    fun provideMoshiBuilder(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(moshi: Moshi): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl("https://dummyapi.io/data/v1/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
    }

    @Singleton
    @Provides
    fun provideUserService(retrofit: Retrofit.Builder): UserRetrofit {
        return retrofit
            .build()
            .create(UserRetrofit::class.java)
    }
}