package com.artemissoftware.orpheusplaylist.di

import com.artemissoftware.orpheusplaylist.data.repositories.AlbumRepositoryImpl
import com.artemissoftware.orpheusplaylist.data.repositories.UserPlaylistDataStoreRepositoryImpl
import com.artemissoftware.orpheusplaylist.domain.repositories.AlbumRepository
import com.artemissoftware.orpheusplaylist.domain.repositories.UserPlaylistDataStoreRepository
import com.artemissoftware.orpheusplaylist.headphone.data.repository.AudioPlayerRepositoryImpl
import com.artemissoftware.orpheusplaylist.headphone.domain.repository.AudioPlayerRepository
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
    abstract fun bindHeadphonePlayerRepository(repository: AudioPlayerRepositoryImpl): AudioPlayerRepository

    @Binds
    @Singleton
    abstract fun bindAlbumRepository(repository: AlbumRepositoryImpl): AlbumRepository

    @Binds
    @Singleton
    abstract fun bindUserPlaylistDataStoreRepository(repository: UserPlaylistDataStoreRepositoryImpl): UserPlaylistDataStoreRepository
}
