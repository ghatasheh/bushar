package com.hisham.bushar.data

import com.hisham.bushar.favourite.domain.FavouriteRepository
import com.hisham.bushar.favourite.domain.state.Favourite
import com.hisham.bushar.home.domain.delegates.FavouriteDelegate
import javax.inject.Inject

class FavouriteDelegateImpl @Inject constructor(
    private val repository: FavouriteRepository,
) : FavouriteDelegate {

    override suspend fun getFavouredIds(): List<Int> {
        return repository.getIds()
    }
    override suspend fun onFavouriteChange(
        id: Int,
        title: String,
        posterUrl: String?,
        favourite: Boolean
    ) {
        if (favourite) {
            repository.save(listOf(Favourite(id, title, posterUrl)))
        } else {
            repository.delete(Favourite(id, title, posterUrl))
        }
    }
}