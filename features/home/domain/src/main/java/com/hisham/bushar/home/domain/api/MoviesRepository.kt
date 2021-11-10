package com.hisham.bushar.home.domain.api

import com.hisham.bushar.home.domain.entities.MovieListItemResponse

interface MoviesRepository {

    suspend fun fetchMovies(page: Int): List<MovieListItemResponse>
}