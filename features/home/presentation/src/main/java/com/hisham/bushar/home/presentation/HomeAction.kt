package com.hisham.bushar.home.presentation

import com.hisham.bushar.home.domain.states.MovieItemState

sealed class HomeAction {

    data class OnMovieClicked(val state: MovieItemState) : HomeAction()

    data class OnFavouriteClicked(val state: MovieItemState) : HomeAction()
}
