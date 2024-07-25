package com.example.mynote.di.datasource

import com.example.mynote.data.sources.network.firebase.AccountRemoteDataSource
import com.example.mynote.data.sources.network.firebase.impl.AccountRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteDataSourceModule {
    @Binds abstract fun provideAccountRemoteDataSource(impl: AccountRemoteDataSourceImpl): AccountRemoteDataSource
}