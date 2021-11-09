package com.hisham.bushar.home.data

import com.app.home.domain.api.MoviesRepository
import com.app.home.domain.entities.MovieListItemResponse
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val remoteDataSource: MoviesRemoteDataSource,
) : MoviesRepository {

    override suspend fun fetchMovies(): List<MovieListItemResponse> {
        return remoteDataSource.listPopularMovies()?.results ?: emptyList()
    }
}