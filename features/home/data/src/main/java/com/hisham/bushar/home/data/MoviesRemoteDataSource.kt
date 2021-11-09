package com.hisham.bushar.home.data

import com.app.home.domain.entities.MovieResponse
import com.app.home.domain.entities.MoviesResponse
import javax.inject.Inject

class MoviesRemoteDataSource @Inject constructor(
    private val api: MoviesApi,
) {
    suspend fun listPopularMovies(): MoviesResponse? {
        return try {
            api.listPopularMovies()
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