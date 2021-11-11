/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hisham.bushar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.hisham.bushar.design.BusharAppTheme
import com.hisham.bushar.favourite.presentation.FavouriteScreen
import com.hisham.bushar.home.presentation.HomeScreen
import com.hisham.bushar.home.presentation.HomeViewModel
import com.hisham.bushar.navigation.HomeDirection
import com.hisham.bushar.navigation.NavigationBarItems
import com.hisham.bushar.navigation.NavigationManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navigationManager: NavigationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            BusharAppTheme {
                HomeScaffold()
            }
        }
    }

    @Composable
    private fun HomeScaffold() {
        val navController = rememberNavController()

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = stringResource(id = R.string.app_name))
                    },
                )
            },
            bottomBar = { BottomBar(navController) }
        ) {
            HomeContentNavigation(navController)
        }
    }

    @Composable
    private fun BottomBar(navController: NavHostController) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        BottomNavigation {

            NavigationBarItems.allItems.forEach {
                BottomNavigationItem(
                    selected = currentRoute == it.route.destination,
                    icon = {
                        Icon(
                            imageVector = it.icon,
                            contentDescription = stringResource(id = it.title),
                        )
                    },
                    selectedContentColor = Color.White,
                    unselectedContentColor = Color.White.copy(0.4f),
                    alwaysShowLabel = true,
                    label = { Text(text = stringResource(id = it.title)) },
                    onClick = { navigationManager.navigate(it.route) })
            }
        }
    }

    @Composable
    private fun HomeContentNavigation(navController: NavHostController) {
        NavHost(
            navController = navController,
            startDestination = HomeDirection.Home.destination,
        ) {
            composable(HomeDirection.Home.destination) {
                val vm: HomeViewModel = hiltViewModel()
                HomeScreen(vm) {}
            }

            composable(HomeDirection.Favourite.destination) {
                FavouriteScreen()
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
}
