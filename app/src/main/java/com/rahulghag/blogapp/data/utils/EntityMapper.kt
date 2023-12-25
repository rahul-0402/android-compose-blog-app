package com.rahulghag.blogapp.data.utils

interface DomainMapper<T, DomainModel> {
    fun mapToDomainModel(model: T): DomainModel
}