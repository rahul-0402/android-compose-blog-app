package com.rahulghag.blogapp.di

import com.rahulghag.blogapp.data.local.PreferencesManager
import com.rahulghag.blogapp.data.remote.ConduitApi
import com.rahulghag.blogapp.data.remote.mappers.UserDomainMapper
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
    fun provideUserDomainMapper(): UserDomainMapper {
        return UserDomainMapper()
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        conduitApi: ConduitApi,
        userDomainMapper: UserDomainMapper,
        preferencesManager: PreferencesManager
    ): AuthRepository {
        return AuthRepositoryImpl(
            conduitApi = conduitApi,
            preferencesManager = preferencesManager,
            userDomainMapper = userDomainMapper
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