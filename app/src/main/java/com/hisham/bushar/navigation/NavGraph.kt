package com.hisham.bushar.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.hisham.bushar.favourite.presentation.FavouriteScreen
import com.hisham.bushar.home.presentation.HomeScreen

@Composable
fun BusharNavigation(
    navController: NavHostController,
    navigationManager: NavigationManager,
) {
    NavHost(
        navController = navController,
        startDestination = HomeDirection.Home.destination,
    ) {
        composable(HomeDirection.Home.destination) {
            HomeScreen(viewModel = hiltViewModel())
        }

        composable(HomeDirection.Favourite.destination) {
            FavouriteScreen(viewModel = hiltViewModel())
        }
    }

    navigationManager.commands.collectAsState().value.also { command ->
        if (command.destination.isNotEmpty()) {
            navController.navigate(command.destination) {
                // Pop up to the start destination of the graph to
                // avoid building up a large stack of destinations
                // on the back stack as users select items
                navController.graph.startDestinationRoute?.let { route ->
                    popUpTo(route) {
                        saveState = true
                    }
                }
                // Avoid multiple copies of the same destination when
                // re-selecting the same item
                launchSingleTop = true
                // Restore state when re-selecting a previously selected item
                restoreState = true
            }
        }
    }
}