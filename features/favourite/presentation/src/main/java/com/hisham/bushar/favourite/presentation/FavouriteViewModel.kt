package com.hisham.bushar.favourite.presentation

import androidx.lifecycle.ViewModel
import com.hisham.bushar.favourite.domain.usecases.FetchFavouriteMoviesUseCase
import com.hisham.bushar.threading.CoroutineDispatchers
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(
    private val fetchFavouriteMoviesUseCase: FetchFavouriteMoviesUseCase,
    private val dispatcher: CoroutineDispatchers,
) : ViewModel() {

    val favouriteFlow = fetchFavouriteMoviesUseCase.observe()
        .flowOn(dispatcher.io)
}
