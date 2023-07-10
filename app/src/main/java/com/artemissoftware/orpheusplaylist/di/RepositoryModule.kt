package com.artemissoftware.orpheusplaylist.di

import com.artemissoftware.orpheusplaylist.data.repository.AudioPlayerRepositoryImpl
import com.artemissoftware.orpheusplaylist.domain.repository.AudioPlayerRepository
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

}