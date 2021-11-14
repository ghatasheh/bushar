package com.hisham.bushar.favourite.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hisham.bushar.favourite.data.database.entites.FavouriteEntity

@Database(
    entities = [FavouriteEntity::class],
    version = 1,
)
abstract class FavouriteDatabase : RoomDatabase() {
    abstract fun favouriteDao(): FavouriteDao
}