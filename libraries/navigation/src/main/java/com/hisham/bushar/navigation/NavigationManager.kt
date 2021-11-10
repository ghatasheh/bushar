package com.hisham.bushar.navigation

import kotlinx.coroutines.flow.MutableStateFlow

class NavigationManager {

    var commands = MutableStateFlow(HomeDirection.Home)

    fun navigate(
        directions: NavigationCommand
    ) {
        commands.value = directions
    }
}
