package com.example.android_architecture_example.network

import com.example.android_architecture_example.domain.User
import com.example.android_architecture_example.util.EntityMapper
import javax.inject.Inject

class NetworkMapper
@Inject
constructor() : EntityMapper<UserDTO, User> {
    override fun mapFromEntity(entity: UserDTO): User {
        return User(
            firstName = entity.firstName,
            id = entity.id,
            lastName = entity.lastName,
            picture = entity.picture,
            title = entity.title,
        )
    }

    override fun mapToEntity(domainModel: User): UserDTO {
        return UserDTO(
            firstName = domainModel.firstName,
            id = domainModel.id,
            lastName = domainModel.lastName,
            picture = domainModel.picture,
            title = domainModel.title,
        )
    }

    fun mapFromEntityList(entities:List<UserDTO>):List<User>{
        return entities.map{ mapFromEntity(it)
        }
    }

    fun mapFromRawData(rawInfo:UserRaw):List<UserDTO>{
        return rawInfo.`data`
    }
}