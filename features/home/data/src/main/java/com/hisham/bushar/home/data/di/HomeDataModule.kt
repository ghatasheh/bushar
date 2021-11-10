package com.hisham.bushar.home.data.di

import com.hisham.bushar.home.data.MoviesRepositoryImpl
import com.hisham.bushar.home.domain.api.MoviesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class HomeDataModule {

    @Binds
    @Singleton
    abstract fun bindMoviesRepository(impl: MoviesRepositoryImpl): MoviesRepository
}
