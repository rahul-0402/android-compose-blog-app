package com.rahulghag.blogapp.di

import com.rahulghag.blogapp.data.remote.ConduitApi
import com.rahulghag.blogapp.data.repositories.UserRepositoryImpl
import com.rahulghag.blogapp.domain.repositories.UserRepository
import com.rahulghag.blogapp.domain.usecases.FollowToggleUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserModule {
    @Provides
    @Singleton
    fun provideUserRepository(
        conduitApi: ConduitApi
    ): UserRepository {
        return UserRepositoryImpl(
            conduitApi = conduitApi
        )
    }

    @Provides
    @Singleton
    fun provideFollowToggleUseCase(userRepository: UserRepository): FollowToggleUseCase {
        return FollowToggleUseCase(userRepository = userRepository)
    }
}