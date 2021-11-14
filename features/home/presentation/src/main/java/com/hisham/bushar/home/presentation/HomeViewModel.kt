package com.hisham.bushar.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.hisham.bushar.home.domain.delegates.FavouriteDelegate
import com.hisham.bushar.home.domain.states.MovieItemState
import com.hisham.bushar.threading.CoroutineDispatchers
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val moviesPager: MoviesPager,
    private val dispatcher: CoroutineDispatchers,
    private val favouriteDelegate: FavouriteDelegate,
) : ViewModel() {

    val moviesPagingFlow: Flow<PagingData<MovieItemState>> = moviesPager.pager()
        .flowOn(dispatcher.io)
        .cachedIn(viewModelScope)

    fun handleAction(action: HomeAction) {
        when (action) {
            is HomeAction.OnMovieClicked -> {

            }
            is HomeAction.OnFavouriteClicked -> {
                handleFavouriteToggle(action.state)
            }
        }
    }

    private fun handleFavouriteToggle(state: MovieItemState) = viewModelScope.launch(dispatcher.io) {
        favouriteDelegate.onFavouriteChange(state.id, state.name, state.coverUrl, state.isFavourite)
    }
}
