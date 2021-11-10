package com.hisham.bushar.home.data

import com.hisham.bushar.home.domain.api.MoviesRepository
import com.hisham.bushar.home.domain.entities.MovieListItemResponse
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val remoteDataSource: MoviesRemoteDataSource,
) : MoviesRepository {

    override suspend fun fetchMovies(page: Int): List<MovieListItemResponse> {
        return remoteDataSource.listPopularMovies(page)?.results ?: emptyList()
    }
}