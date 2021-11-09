package com.app.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.home.domain.entities.MoviesListState
import com.app.home.domain.usecases.FetchMoviesUseCase
import com.app.navigation.NavigationManager
import com.app.threading.CoroutineDispatchers
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val navigationManager: NavigationManager,
    private val fetchMoviesUseCase: FetchMoviesUseCase,
    private val dispatcher: CoroutineDispatchers,
) : ViewModel() {

    private val _uiState = MutableStateFlow(MoviesListState())
    val uiState: StateFlow<MoviesListState> = _uiState

    init {
        viewModelScope.launch(dispatcher.io) {
            val moviesListState = fetchMoviesUseCase.execute()
            _uiState.value = moviesListState
        }
    }

    fun handleEvent(event: HomeEvent) {
    }
}
