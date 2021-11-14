package com.hisham.bushar.favourite.data.di

import android.content.Context
import androidx.room.Room
import com.hisham.bushar.favourite.domain.FavouriteRepository
import com.hisham.bushar.favourite.data.FavouriteRepositoryImpl
import com.hisham.bushar.favourite.data.database.FavouriteDao
import com.hisham.bushar.favourite.data.database.FavouriteDatabase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class FavouriteDataModule {

    @Binds
    @Singleton
    abstract fun bindsFavouriteRepository(impl: FavouriteRepositoryImpl) : FavouriteRepository

    companion object {
        @Provides
        @Singleton
        fun provideFavouriteDatabase(
            @ApplicationContext context: Context,
        ) : FavouriteDatabase {
            return Room.databaseBuilder(
                context,
                FavouriteDatabase::class.java, "favourites"
            ).build()
        }

        @Provides
        @Singleton
        fun provideFavouriteDao(
            database: FavouriteDatabase,
        ) : FavouriteDao {
            return database.favouriteDao()
        }
    }
}