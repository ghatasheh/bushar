package com.hisham.bushar.home.domain.usecases

import com.hisham.bushar.home.domain.api.MoviesRepository
import com.hisham.bushar.home.domain.delegates.FavouriteDelegate
import com.hisham.bushar.home.domain.entities.MovieListItemResponse
import com.hisham.bushar.home.domain.states.MovieItemState
import javax.inject.Inject

class FetchMoviesUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val favouriteDelegate: FavouriteDelegate,
) {
    suspend fun execute(page: Int): List<MovieItemState> {
        val list = moviesRepository.fetchMovies(page)
        val ids = favouriteDelegate.getFavouredIds()

        return toUiData(list, ids)
    }

    private fun toUiData(
        list: List<MovieListItemResponse>,
        ids: List<Int>
    ): List<MovieItemState> {
        return list.map {
            val posterUrl = if (it.posterPath.isNullOrEmpty()) {
                null
            } else {
                "https://image.tmdb.org/t/p/w500${it.posterPath}"
            }

            MovieItemState(
                id = it.id,
                name = it.title,
                coverUrl = posterUrl,
                isFavourite = ids.contains(it.id)
            )
        }
    }
}
