package com.hisham.bushar.home.domain.delegates

interface FavouriteDelegate {

    suspend fun getFavouredIds(): List<Int>

    suspend fun onFavouriteChange(
        id: Int,
        title: String,
        posterUrl: String?,
        favourite: Boolean
    )
}
