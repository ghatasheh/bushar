package com.hisham.bushar.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.hisham.bushar.home.domain.states.MovieItemState
import com.hisham.bushar.navigation.NavigationManager
import com.hisham.bushar.threading.CoroutineDispatchers
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val navigationManager: NavigationManager,
    private val moviesPager: MoviesPager,
    private val dispatcher: CoroutineDispatchers,
) : ViewModel() {

    val moviesPagingFlow: Flow<PagingData<MovieItemState>> = moviesPager.pager()
        .cachedIn(viewModelScope)

    fun handleEvent(event: HomeEvent) {
    }
}
