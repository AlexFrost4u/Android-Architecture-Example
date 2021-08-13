package com.example.android_architecture_example.database

import com.example.android_architecture_example.domain.User
import com.example.android_architecture_example.util.EntityMapper
import javax.inject.Inject

class CacheMapper
@Inject
constructor() : EntityMapper<UserEntity, User> {
    override fun mapFromEntity(entity: UserEntity): User {
        return User(
            id = entity.id,
            title = entity.title,
            firstName = entity.firstName,
            lastName = entity.lastName,
            email = entity.email,
            picture = entity.picture,
        )
    }

    override fun mapToEntity(domainModel: User): UserEntity {
        return UserEntity(
            id = domainModel.id,
            title = domainModel.title,
            firstName = domainModel.firstName,
            lastName = domainModel.lastName,
            email = domainModel.email,
            picture = domainModel.picture,
        )
    }

    fun mapFromEntityList(entities:List<UserEntity>):List<User>{
        return entities.map{mapFromEntity(it)}
    }
}