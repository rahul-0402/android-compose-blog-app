package com.rahulghag.blogapp.data.remote.mappers

import com.rahulghag.blogapp.data.remote.dtos.response.UserDto
import com.rahulghag.blogapp.data.utils.DomainMapper
import com.rahulghag.blogapp.domain.models.User

class UserDomainMapper : DomainMapper<UserDto, User> {
    override fun mapToDomainModel(data: UserDto): User {
        return User(
            bio = data.bio,
            email = data.email,
            image = data.image,
            token = data.token,
            username = data.username
        )
    }
}