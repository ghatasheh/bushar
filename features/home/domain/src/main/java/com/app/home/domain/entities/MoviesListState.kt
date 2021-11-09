package com.app.home.domain.entities

data class MoviesListState(
    val movies: List<MoviesListItemState> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null,
)

data class MoviesListItemState(
    val id: Int,
    val name: String,
    val coverUrl: String?,
)