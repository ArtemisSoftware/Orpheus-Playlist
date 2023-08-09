package com.artemissoftware.orpheusplaylist.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.artemissoftware.orpheusplaylist.presentation.player.MusicPlayer

val albumCoverSize = 240.dp
val bottomPlayerHeight = 140.dp

@Composable
fun RootNavigationGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = "root_navigation_graph",
        startDestination = Screens.Player.fullRoute(),
    ) {
        composable(route = Screens.Player.fullRoute()) { entry ->

            MusicPlayer()
        }
    }
}
