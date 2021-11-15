package com.hisham.bushar.favourite.data.mapper

import com.hisham.bushar.favourite.data.database.entites.FavouriteEntity
import com.hisham.bushar.favourite.domain.state.Favourite

internal fun FavouriteEntity.asState() = Favourite(
    id = id,
    title = title,
    poster = posterUrl
)

internal fun Favourite.asEntity() = FavouriteEntity(
    id = id,
    title = title,
    posterUrl = poster
)
