package com.hisham.bushar.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Star
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavigationBarItems(
    val route: NavigationCommand,
    val icon: ImageVector,
    val title: Int,
) {

    object Home : NavigationBarItems(
        HomeDirection.Home,
        Icons.Rounded.Home,
        com.hisham.bushar.R.string.home
    )

    object Favourite : NavigationBarItems(
        HomeDirection.Favourite,
        Icons.Rounded.Star,
        com.hisham.bushar.R.string.favourite
    )

    companion object {
        val allItems = listOf(
            Home,
            Favourite,
        )
    }


}