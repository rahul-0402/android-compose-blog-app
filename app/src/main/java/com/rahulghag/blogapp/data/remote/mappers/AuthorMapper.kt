package com.rahulghag.blogapp.data.remote.mappers

import com.rahulghag.blogapp.data.remote.dtos.response.AuthorDto
import com.rahulghag.blogapp.data.utils.DomainMapper
import com.rahulghag.blogapp.domain.models.Author

class AuthorMapper : DomainMapper<AuthorDto?, Author> {
    override fun mapToDomainModel(data: AuthorDto?): Author {
        return Author(
            bio = data?.bio,
            isFollowing = data?.following ?: false,
            image = data?.image,
            username = data?.username
        )
    }
}