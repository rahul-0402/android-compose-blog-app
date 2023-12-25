package com.rahulghag.blogapp.data.remote.mappers

import com.rahulghag.blogapp.data.remote.dtos.response.UserDto
import com.rahulghag.blogapp.data.utils.DomainMapper
import com.rahulghag.blogapp.domain.models.User

class UserDomainMapper : DomainMapper<UserDto, User> {
    override fun mapToDomainModel(model: UserDto): User {
        return User(
            bio = model.bio,
            email = model.email,
            image = model.image,
            token = model.token,
            username = model.username
        )
    }
}