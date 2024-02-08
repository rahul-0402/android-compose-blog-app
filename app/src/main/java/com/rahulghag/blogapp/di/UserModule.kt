package com.rahulghag.blogapp.di

import com.rahulghag.blogapp.data.remote.ConduitApi
import com.rahulghag.blogapp.data.repositories.UserRepositoryImpl
import com.rahulghag.blogapp.domain.repositories.UserRepository
import com.rahulghag.blogapp.domain.usecases.FollowUseCase
import com.rahulghag.blogapp.domain.usecases.UnfollowUseCase
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
    fun provideFollowUseCase(userRepository: UserRepository): FollowUseCase {
        return FollowUseCase(userRepository = userRepository)
    }

    @Provides
    @Singleton
    fun provideUnfollowUseCase(userRepository: UserRepository): UnfollowUseCase {
        return UnfollowUseCase(userRepository = userRepository)
    }
}