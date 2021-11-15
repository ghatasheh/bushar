package com.hisham.bushar.favourite.data.sources

import com.hisham.bushar.favourite.data.database.FavouriteDao
import com.hisham.bushar.favourite.data.database.entites.FavouriteEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavouritesLocalDataSource @Inject constructor(
    private val favouriteDao: FavouriteDao,
) {

    fun observeAll(): Flow<List<FavouriteEntity>> {
        return favouriteDao.observeAll()
    }

    suspend fun getIds(): List<Int> {
        return favouriteDao.getIds()
    }

    suspend fun addOrReplace(entities: List<FavouriteEntity>) {
        favouriteDao.insertAll(*entities.toTypedArray())
    }

    suspend fun delete(entity: FavouriteEntity) {
        favouriteDao.delete(entity)
    }
}
