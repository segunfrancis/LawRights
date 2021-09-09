package com.segunfrancis.data.remote.di

import com.segunfrancis.data.remote.repository.RemoteRepository
import com.segunfrancis.data.remote.repository.RemoteRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
interface RepositoryModule {

    @Binds
    fun bindRemoteRepository(remoteRepositoryImpl: RemoteRepositoryImpl): RemoteRepository
}
