package com.app.home.domain.api

import com.app.home.domain.entities.MovieListItemResponse

interface MoviesRepository {

    suspend fun fetchMovies(): List<MovieListItemResponse>
}