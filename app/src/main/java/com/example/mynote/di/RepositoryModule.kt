package com.example.mynote.di

import com.example.mynote.data.repositories.AccountRepository
import com.example.mynote.data.repositories.impl.AccountRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds abstract fun provideAccountRepository(impl: AccountRepositoryImpl): AccountRepository
}