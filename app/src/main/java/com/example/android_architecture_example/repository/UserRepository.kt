package com.example.android_architecture_example.repository

import com.example.android_architecture_example.database.CacheMapper
import com.example.android_architecture_example.database.UserDao
import com.example.android_architecture_example.domain.User
import com.example.android_architecture_example.domain.UserFull
import com.example.android_architecture_example.network.NetworkUserFullMapper
import com.example.android_architecture_example.network.NetworkUserMapper
import com.example.android_architecture_example.network.UserRetrofit
import com.example.android_architecture_example.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UserRepository
constructor(
    private val userDao: UserDao,
    private val userRetrofit: UserRetrofit,
    private val cacheMapper: CacheMapper,
    private val networkUserMapper: NetworkUserMapper,
    private val networkUserFullMapper: NetworkUserFullMapper
) {
    suspend fun getUser(): Flow<DataState<List<User>>> = flow {
        emit(DataState.Loading)
        try {
            val rawData = networkUserMapper.mapFromRawData(userRetrofit.getAll())
            val users = networkUserMapper.mapFromEntityList(rawData)
            for (user in users) {
                userDao.insert(cacheMapper.mapToEntity(user))
            }
            val cachedUsers = userDao.getAll()
            emit(DataState.Success(cacheMapper.mapFromEntityList(cachedUsers)))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    suspend fun getUserFull(id: String): Flow<DataState<UserFull>> = flow {
        emit(DataState.Loading)
        try {
            val user = networkUserFullMapper.mapFromEntity(userRetrofit.getUser(id))
            emit(DataState.Success(user))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }
}