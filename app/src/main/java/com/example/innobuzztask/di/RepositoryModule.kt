package com.example.innobuzztask.di

import com.example.innobuzztask.data.repositories.AppRepositoryImpl
import com.example.innobuzztask.domain.repository.AppRepository
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
    abstract fun bindsAppRepository(impl: AppRepositoryImpl): AppRepository
}