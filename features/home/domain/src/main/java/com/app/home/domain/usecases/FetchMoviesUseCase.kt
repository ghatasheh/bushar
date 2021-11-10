package com.app.home.domain.usecases

import com.app.home.domain.api.MoviesRepository
import com.app.home.domain.entities.MovieListItemResponse
import com.app.home.domain.entities.MovieItemState
import javax.inject.Inject

class FetchMoviesUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository,
) {
    suspend fun execute(page: Int): List<MovieItemState> {
        val list = moviesRepository.fetchMovies(page)

        return toUiData(list)
    }

    private fun toUiData(list: List<MovieListItemResponse>): List<MovieItemState> {
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
            )
        }
    }
}