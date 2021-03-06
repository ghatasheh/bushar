package com.hisham.bushar.home.data

import com.hisham.bushar.home.domain.entities.MovieResponse
import com.hisham.bushar.home.domain.entities.MoviesResponse
import javax.inject.Inject

class MoviesRemoteDataSource @Inject constructor(
    private val api: MoviesApi,
) {
    suspend fun listPopularMovies(page: Int): MoviesResponse? {
        return try {
            api.listPopularMovies(page)
        } catch (e: Exception) {
            if (BuildConfig.DEBUG) {
                println(e)
            }
            null
        }
    }

    suspend fun movie(movieId: Int): MovieResponse? {
        return try {
            api.movie(movieId)
        } catch (e: Exception) {
            if (BuildConfig.DEBUG) {
                println(e)
            }
            null
        }
    }
}
