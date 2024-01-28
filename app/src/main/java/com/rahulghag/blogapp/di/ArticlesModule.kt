package com.rahulghag.blogapp.di

import com.rahulghag.blogapp.data.remote.ConduitApi
import com.rahulghag.blogapp.data.remote.mappers.ArticlesMapper
import com.rahulghag.blogapp.data.remote.mappers.AuthorMapper
import com.rahulghag.blogapp.data.remote.mappers.CommentsMapper
import com.rahulghag.blogapp.data.repositories.articles.ArticlesRepositoryImpl
import com.rahulghag.blogapp.domain.repositories.ArticlesRepository
import com.rahulghag.blogapp.domain.usecases.AddCommentUseCase
import com.rahulghag.blogapp.domain.usecases.DeleteCommentUseCase
import com.rahulghag.blogapp.domain.usecases.GetArticlesUseCase
import com.rahulghag.blogapp.domain.usecases.GetCommentsUseCase
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
    fun provideAuthorMapper(): AuthorMapper {
        return AuthorMapper()
    }

    @Provides
    @Singleton
    fun provideArticlesMapper(authorMapper: AuthorMapper): ArticlesMapper {
        return ArticlesMapper(authorMapper = authorMapper)
    }

    @Provides
    @Singleton
    fun provideCommentsMapper(authorMapper: AuthorMapper): CommentsMapper {
        return CommentsMapper(authorMapper = authorMapper)
    }

    @Provides
    @Singleton
    fun provideArticlesRepository(
        conduitApi: ConduitApi,
        articlesMapper: ArticlesMapper,
        commentsMapper: CommentsMapper
    ): ArticlesRepository {
        return ArticlesRepositoryImpl(
            conduitApi = conduitApi,
            articlesMapper = articlesMapper,
            commentsMapper = commentsMapper
        )
    }

    @Provides
    @Singleton
    fun provideGetArticlesUseCase(articlesRepository: ArticlesRepository): GetArticlesUseCase {
        return GetArticlesUseCase(articlesRepository = articlesRepository)
    }

    @Provides
    @Singleton
    fun provideGetCommentsUseCase(articlesRepository: ArticlesRepository): GetCommentsUseCase {
        return GetCommentsUseCase(articlesRepository = articlesRepository)
    }

    @Provides
    @Singleton
    fun provideDeleteCommentUseCase(articlesRepository: ArticlesRepository): DeleteCommentUseCase {
        return DeleteCommentUseCase(articlesRepository = articlesRepository)
    }

    @Provides
    @Singleton
    fun provideAddCommentUseCase(articlesRepository: ArticlesRepository): AddCommentUseCase {
        return AddCommentUseCase(articlesRepository = articlesRepository)
    }
}