package com.example.android_architecture_example.network

import com.example.android_architecture_example.domain.Location
import com.example.android_architecture_example.domain.UserFull
import com.example.android_architecture_example.util.EntityMapper
import javax.inject.Inject

class NetworkUserFullMapper
@Inject
constructor() : EntityMapper<UserFullDTO, UserFull> {
    override fun mapFromEntity(entity: UserFullDTO): UserFull {
        return UserFull(
            dateOfBirth = entity.dateOfBirth,
            email = entity.email,
            firstName = entity.firstName,
            gender = entity.gender,
            id = entity.id,
            lastName = entity.lastName,
            location = mapToLocation(entity.location),
            phone = entity.phone,
            picture = entity.picture,
            registerDate = entity.registerDate,
            title = entity.title,
            updatedDate = entity.updatedDate,
        )
    }

    override fun mapToEntity(domainModel: UserFull): UserFullDTO {
        return UserFullDTO(
            dateOfBirth = domainModel.dateOfBirth,
            email = domainModel.email,
            firstName = domainModel.firstName,
            gender = domainModel.gender,
            id = domainModel.id,
            lastName = domainModel.lastName,
            location = mapFromLocation(domainModel.location),
            phone = domainModel.phone,
            picture = domainModel.picture,
            registerDate = domainModel.registerDate,
            title = domainModel.title,
            updatedDate = domainModel.updatedDate

        )
    }

    private fun mapToLocation(entity: LocationDTO): Location {
        return Location(
            city = entity.city,
            country = entity.country,
            state = entity.state,
            street = entity.street,
            timezone = entity.timezone
        )
    }

    private fun mapFromLocation(domainModel: Location): LocationDTO {
        return LocationDTO(
            city = domainModel.city,
            country = domainModel.country,
            state = domainModel.state,
            street = domainModel.street,
            timezone = domainModel.timezone
        )
    }
}