package com.rahulghag.blogapp.di

import com.rahulghag.blogapp.data.local.PreferencesManager
import com.rahulghag.blogapp.data.remote.ConduitApi
import com.rahulghag.blogapp.data.remote.mappers.UserMapper
import com.rahulghag.blogapp.data.repositories.AuthRepositoryImpl
import com.rahulghag.blogapp.domain.repositories.AuthRepository
import com.rahulghag.blogapp.domain.usecases.CheckUserLoginStatusUseCase
import com.rahulghag.blogapp.domain.usecases.CreateAccountUseCase
import com.rahulghag.blogapp.domain.usecases.LoginUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {
    @Provides
    @Singleton
    fun provideUserMapper(): UserMapper {
        return UserMapper()
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        conduitApi: ConduitApi,
        userMapper: UserMapper,
        preferencesManager: PreferencesManager
    ): AuthRepository {
        return AuthRepositoryImpl(
            conduitApi = conduitApi,
            preferencesManager = preferencesManager,
            userMapper = userMapper
        )
    }

    @Provides
    @Singleton
    fun provideLoginUseCase(authRepository: AuthRepository): LoginUseCase {
        return LoginUseCase(authRepository = authRepository)
    }

    @Provides
    @Singleton
    fun provideCreateAccountUseCase(authRepository: AuthRepository): CreateAccountUseCase {
        return CreateAccountUseCase(authRepository = authRepository)
    }

    @Provides
    @Singleton
    fun provideCheckUserLoginStatusUseCase(preferencesManager: PreferencesManager): CheckUserLoginStatusUseCase {
        return CheckUserLoginStatusUseCase(preferencesManager = preferencesManager)
    }
}