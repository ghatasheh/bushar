package com.hisham.bushar.favourite.domain

import com.hisham.bushar.favourite.domain.state.Favourite
import kotlinx.coroutines.flow.Flow

interface FavouriteRepository {

    fun observeAll(): Flow<List<Favourite>>

    suspend fun getIds(): List<Int>

    suspend fun delete(favourite: Favourite)

    suspend fun save(favourites: List<Favourite>)
}
