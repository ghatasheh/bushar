package com.hisham.bushar.favourite.domain.usecases

import com.hisham.bushar.favourite.domain.FavouriteRepository
import com.hisham.bushar.favourite.domain.state.Favourite
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchFavouriteMoviesUseCase @Inject constructor(
    private val favouriteRepository: FavouriteRepository,
) {

    fun observe(): Flow<List<Favourite>> {
        return favouriteRepository.observeAll()
    }
}
