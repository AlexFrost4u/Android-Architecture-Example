package com.example.android_architecture_example.di

import com.example.android_architecture_example.database.CacheMapper
import com.example.android_architecture_example.database.UserDao
import com.example.android_architecture_example.network.NetworkMapper
import com.example.android_architecture_example.network.UserRetrofit
import com.example.android_architecture_example.repository.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideMainRepository(
        userDao: UserDao,
        retrofit: UserRetrofit,
        cacheMapper: CacheMapper,
        networkMapper: NetworkMapper
    ): MainRepository {
        return MainRepository(userDao, retrofit, cacheMapper, networkMapper)
    }
}