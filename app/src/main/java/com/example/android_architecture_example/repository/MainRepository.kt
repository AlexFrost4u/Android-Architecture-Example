package com.example.android_architecture_example.repository

import com.example.android_architecture_example.database.CacheMapper
import com.example.android_architecture_example.database.UserDao
import com.example.android_architecture_example.domain.User
import com.example.android_architecture_example.network.NetworkMapper
import com.example.android_architecture_example.network.UserRetrofit
import com.example.android_architecture_example.util.DataState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MainRepository
constructor(
    private val userDao: UserDao,
    private val userRetrofit: UserRetrofit,
    private val cacheMapper: CacheMapper,
    private val networkMapper: NetworkMapper
    ){
    suspend fun getUser(): Flow<DataState<List<User>>> = flow {
        emit(DataState.Loading)
        delay(1000)
        try{
            val networkUsers = userRetrofit.get()
            val users = networkMapper.mapFromEntityList(networkUsers)
            for(user in users){
                userDao.insert(cacheMapper.mapToEntity(user))
            }
            val cachedUsers = userDao.get()
            emit(DataState.Success(cacheMapper.mapFromEntityList(cachedUsers)))
        }catch(e:Exception){
            emit(DataState.Error(e))
        }
    }
}