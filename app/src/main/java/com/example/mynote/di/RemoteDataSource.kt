package com.example.mynote.di

import com.example.mynote.data.sources.network.firebase.AccountRemoteDataSource
import com.example.mynote.data.sources.network.firebase.NoteRemoteDataSource
import com.example.mynote.data.sources.network.firebase.impl.AccountRemoteDataSourceImpl
import com.example.mynote.data.sources.network.firebase.impl.NoteRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RemoteDataSource {
    @Binds
    abstract fun bindAccountRemoteDataSource(
        accountRemoteDataSourceImpl: AccountRemoteDataSourceImpl
    ): AccountRemoteDataSource

    @Binds
    abstract fun bindNoteRemoteDataSource(
        noteRemoteDataSourceImpl: NoteRemoteDataSourceImpl
    ): NoteRemoteDataSource
}