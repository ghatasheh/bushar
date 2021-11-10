package com.hisham.bushar.home.presentation

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.hisham.bushar.home.data.MoviesPagingSource
import com.hisham.bushar.home.domain.states.MovieItemState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MoviesPager @Inject constructor(
    private val moviesPagingSource: MoviesPagingSource
) {

    @OptIn(ExperimentalPagingApi::class)
    fun pager(): Flow<PagingData<MovieItemState>> {
        return Pager(
            config = PagingConfig(pageSize = 50, enablePlaceholders = false),
            pagingSourceFactory = { moviesPagingSource }
        ).flow
    }
}
