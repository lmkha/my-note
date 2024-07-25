package com.example.mynote.di.datasource

import com.example.mynote.data.sources.network.firebase.AccountRemoteDataSource
import com.example.mynote.data.sources.network.firebase.NoteRemoteDataSource
import com.example.mynote.data.sources.network.firebase.impl.AccountRemoteDataSourceImpl
import com.example.mynote.data.sources.network.firebase.impl.NoteRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteDataSourceModule {
    @Binds abstract fun provideAccountRemoteDataSource(impl: AccountRemoteDataSourceImpl): AccountRemoteDataSource
    @Binds abstract fun provideNoteRemoteDataSource(impl: NoteRemoteDataSourceImpl): NoteRemoteDataSource
}