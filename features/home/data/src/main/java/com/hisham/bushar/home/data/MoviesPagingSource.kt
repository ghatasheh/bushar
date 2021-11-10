package com.hisham.bushar.home.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.hisham.bushar.home.domain.states.MovieItemState
import com.hisham.bushar.home.domain.usecases.FetchMoviesUseCase
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class MoviesPagingSource @Inject constructor(
    private val fetchMoviesUseCase: FetchMoviesUseCase,
) : PagingSource<Int, MovieItemState>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieItemState> {
        val position = params.key ?: STARTING_PAGE_INDEX
        return try {
            val moviesList = fetchMoviesUseCase.execute(position)

            val nextKey = if (moviesList.isEmpty()) {
                null
            } else {
                position + 1
            }

            LoadResult.Page(
                data = moviesList,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    /**
     * The refresh key is used for subsequent refresh calls to PagingSource.load after the initial load
     */
    override fun getRefreshKey(state: PagingState<Int, MovieItemState>): Int? {
        // We need to get the previous key (or next key if previous is null) of the page
        // that was closest to the most recently accessed index.
        // Anchor position is the most recently accessed index
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    companion object {
        const val STARTING_PAGE_INDEX = 1
    }
}