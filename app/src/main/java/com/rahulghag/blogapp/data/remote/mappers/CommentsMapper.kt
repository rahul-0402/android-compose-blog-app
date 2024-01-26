package com.rahulghag.blogapp.data.remote.mappers

import com.rahulghag.blogapp.data.remote.dtos.response.CommentDto
import com.rahulghag.blogapp.data.utils.DomainMapper
import com.rahulghag.blogapp.domain.models.Comment

class CommentsMapper(private val authorMapper: AuthorMapper) :
    DomainMapper<List<CommentDto>, List<Comment>> {
    override fun mapToDomainModel(data: List<CommentDto>): List<Comment> {
        return data.map { commentDto ->
            Comment(
                author = authorMapper.mapToDomainModel(commentDto.authorDto),
                body = commentDto.body,
                createdAt = commentDto.createdAt,
                id = commentDto.id,
                updatedAt = commentDto.updatedAt
            )
        }
    }
}