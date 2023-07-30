package com.artemissoftware.orpheusplaylist.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Screens(
    private val route: String,
    val arguments: List<NamedNavArgument> = emptyList(),
) {

    object Albums : Screens(route = "Albums")
    object Playlist : Screens(
        route = "Playlist",
        arguments = listOf(navArgument("albumId") { type = NavType.LongType }),
    ) {
        const val albumId = "albumId"
    }

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }

    fun fullRoute(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/{$arg}")
            }
        }
    }
}
