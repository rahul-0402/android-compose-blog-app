package com.rahulghag.blogapp.data.utils

interface DomainMapper<T, DomainModel> {
    fun mapToDomainModel(data: T): DomainModel
}