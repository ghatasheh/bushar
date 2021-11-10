package com.app.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.app.home.domain.entities.MovieItemState
import com.app.navigation.NavigationManager
import com.app.threading.CoroutineDispatchers
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
