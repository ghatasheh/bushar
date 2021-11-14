package com.hisham.bushar.home.data

import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import com.hisham.bushar.home.domain.entities.MovieListItemResponse
import com.hisham.bushar.home.domain.entities.MoviesResponse
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test

internal class MoviesRepositoryImplTest {

    private val moviesRemoteDataSource: MoviesRemoteDataSource = mockk()

    private val repository = MoviesRepositoryImpl(moviesRemoteDataSource)

    @Test
    fun `test fetch movies`() = runBlocking {
        val response = MoviesResponse(
            1,
            listOf(movieListItemResponseStub()),
            10,
            100
        )

        coEvery {  moviesRemoteDataSource.listPopularMovies(1) } returns response

        val result = repository.fetchMovies(1)

        assertThat(result.size).isEqualTo(1)
        assertThat(result.first().id).isEqualTo(response.results.first().id)
    }

    @Test
    fun `test fetch movies returns empty list when response is null`() = runBlocking {
        coEvery {  moviesRemoteDataSource.listPopularMovies(1) } returns null

        val result = repository.fetchMovies(1)

        assertThat(result.size).isEqualTo(0)
    }

    private fun movieListItemResponseStub() = MovieListItemResponse(
        adult = false,
        backdropPath = "url",
        genreIds = emptyList(),
        id = 1,
        originalLanguage = "en",
        originalTitle = "Title",
        overview = "overview",
        popularity = 7.0,
        posterPath = "path",
        releaseDate = "01/01/2021",
        title = "Title",
        video = false,
        voteAverage = 7.5,
        voteCount = 100
    )
}