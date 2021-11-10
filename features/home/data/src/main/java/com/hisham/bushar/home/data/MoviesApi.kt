package com.hisham.bushar.home.data

import com.hisham.bushar.home.domain.entities.MovieResponse
import com.hisham.bushar.home.domain.entities.MoviesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApi {

    @GET("movie/popular")
    suspend fun listPopularMovies(
        @Query("page") page: Int,
        @Query("api_key") appId: String = BuildConfig.API_KEY,
    ): MoviesResponse

    @GET("movie/{movie_id}")
    suspend fun movie(
        movieId: Int,
        @Query("api_key") appId: String = BuildConfig.API_KEY,
    ): MovieResponse
}