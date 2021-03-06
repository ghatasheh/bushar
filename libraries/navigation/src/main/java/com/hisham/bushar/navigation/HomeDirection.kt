package com.hisham.bushar.navigation

import androidx.navigation.NamedNavArgument

object HomeDirection {

    val Home = object : NavigationCommand {
        override val arguments: List<NamedNavArgument>
            get() = emptyList()

        override val destination: String
            get() = "home"
    }

    val Favourite = object : NavigationCommand {
        override val arguments: List<NamedNavArgument>
            get() = emptyList()
        override val destination: String
            get() = "favourite"
    }
}
