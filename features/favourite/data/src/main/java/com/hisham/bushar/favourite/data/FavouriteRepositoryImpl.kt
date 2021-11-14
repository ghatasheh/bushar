package com.hisham.bushar.favourite.data

import com.hisham.bushar.favourite.data.mapper.asEntity
import com.hisham.bushar.favourite.data.mapper.asState
import com.hisham.bushar.favourite.data.sources.FavouritesLocalDataSource
import com.hisham.bushar.favourite.domain.FavouriteRepository
import com.hisham.bushar.favourite.domain.state.Favourite
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavouriteRepositoryImpl @Inject constructor(
    private val favouritesLocalDataSource: FavouritesLocalDataSource,
) : FavouriteRepository {

    override fun observeAll(): Flow<List<Favourite>> {
        return favouritesLocalDataSource.observeAll().map { list -> list.map { it.asState() } }
    }

    override suspend fun getIds(): List<Int> {
        return favouritesLocalDataSource.getIds()
    }

    override suspend fun delete(favourite: Favourite) {
        favouritesLocalDataSource.delete(favourite.asEntity())
    }

    override suspend fun save(favourites: List<Favourite>) {
        favouritesLocalDataSource.addOrReplace(
            favourites.map { it.asEntity() }
        )
    }
}
