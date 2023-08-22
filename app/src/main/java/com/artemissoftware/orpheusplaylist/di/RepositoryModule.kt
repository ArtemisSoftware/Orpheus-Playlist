package com.artemissoftware.orpheusplaylist.di

import com.artemissoftware.orpheusplaylist.data.repositories.AlbumRepositoryImpl
import com.artemissoftware.orpheusplaylist.data.repositories.UserPlaylistDataStoreRepositoryImpl
import com.artemissoftware.orpheusplaylist.domain.repositories.AlbumRepository
import com.artemissoftware.orpheusplaylist.domain.repositories.AudioRepository
import com.artemissoftware.orpheusplaylist.domain.repositories.UserPlaylistDataStoreRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAudioRepository(repository: AudioRepository): AudioRepository

    @Binds
    @Singleton
    abstract fun bindAlbumRepository(repository: AlbumRepositoryImpl): AlbumRepository

    @Binds
    @Singleton
    abstract fun bindUserPlaylistDataStoreRepository(repository: UserPlaylistDataStoreRepositoryImpl): UserPlaylistDataStoreRepository
}
