package com.app.home.domain.usecases

import com.app.home.domain.api.MoviesRepository
import com.app.home.domain.entities.MovieListItemResponse
import com.app.home.domain.entities.MoviesListItemState
import com.app.home.domain.entities.MoviesListState
import javax.inject.Inject

class FetchMoviesUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository,
) {

    suspend fun execute(): MoviesListState {
        val list = moviesRepository.fetchMovies()
        if (list.isEmpty()) {
            return MoviesListState(isLoading = false, error = "Opps!")
        }

        return MoviesListState(isLoading = false, movies = toUiData(list))
    }

    private fun toUiData(list: List<MovieListItemResponse>): List<MoviesListItemState> {
        return list.map {
            val posterUrl = if (it.posterPath.isNullOrEmpty()) {
                null
            } else {
                "https://image.tmdb.org/t/p/w500${it.posterPath}"
            }

            MoviesListItemState(
                id = it.id,
                name = it.title,
                coverUrl = posterUrl,
            )
        }
    }
}