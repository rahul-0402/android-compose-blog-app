package com.rahulghag.blogapp.di

import com.rahulghag.blogapp.data.remote.ConduitApi
import com.rahulghag.blogapp.data.remote.mappers.ArticleDomainMapper
import com.rahulghag.blogapp.data.remote.mappers.AuthorDomainMapper
import com.rahulghag.blogapp.data.repositories.articles.ArticlesRepositoryImpl
import com.rahulghag.blogapp.domain.repositories.ArticlesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ArticlesModule {
    @Provides
    @Singleton
    fun provideAuthorDomainMapper(): AuthorDomainMapper {
        return AuthorDomainMapper()
    }

    @Provides
    @Singleton
    fun provideArticleDomainMapper(authorDomainMapper: AuthorDomainMapper): ArticleDomainMapper {
        return ArticleDomainMapper(authorDomainMapper = authorDomainMapper)
    }

    @Provides
    @Singleton
    fun provideArticlesRepository(
        conduitApi: ConduitApi,
        articleDomainMapper: ArticleDomainMapper
    ): ArticlesRepository {
        return ArticlesRepositoryImpl(
            conduitApi = conduitApi,
            articleDomainMapper = articleDomainMapper
        )
    }
}