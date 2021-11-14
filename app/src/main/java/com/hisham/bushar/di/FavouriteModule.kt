package com.hisham.bushar.di

import com.hisham.bushar.data.FavouriteDelegateImpl
import com.hisham.bushar.home.domain.delegates.FavouriteDelegate
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class FavouriteModule {

    @Singleton
    @Binds
    abstract fun bindsFavouriteDelegate(impl: FavouriteDelegateImpl): FavouriteDelegate
}