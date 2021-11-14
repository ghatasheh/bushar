package com.hisham.bushar.home.data

import com.google.common.truth.Truth.assertThat
import com.hisham.bushar.home.domain.entities.MoviesResponse
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test

internal class MoviesRemoteDataSourceTest {

    private val api: MoviesApi = mockk()

    private val dataSource: MoviesRemoteDataSource = MoviesRemoteDataSource(api)

    @Test
    fun `test list popular movies`() = runBlocking {
        val response: MoviesResponse = mockk(relaxed = true)
        coEvery { api.listPopularMovies(1) } returns response

        val result = dataSource.movie(1)

        assertThat(result).isEqualTo(response)
    }
}