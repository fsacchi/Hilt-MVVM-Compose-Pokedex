package com.fsacchi.hilt_mvvm_compose_pokedex.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.fsacchi.hilt_mvvm_compose_pokedex.R
import com.fsacchi.hilt_mvvm_compose_pokedex.data.model.ResultItem
import com.fsacchi.hilt_mvvm_compose_pokedex.ui.screens.bottomnavigation.pokemon.AllPokemon
import com.fsacchi.hilt_mvvm_compose_pokedex.ui.screens.detail.PokemonDetail
import com.fsacchi.hilt_mvvm_compose_pokedex.ui.screens.types.TypePokemonScreen


@Composable
fun Navigation(
    navController: NavHostController, modifier: Modifier,  types: ArrayList<ResultItem>? = null,
) {
    NavHost(navController, startDestination = Screen.Home.route, modifier) {
        composable(Screen.Home.route) {
            AllPokemon(
                navController = navController,
                types
            )
        }
        composable(
            Screen.NavigationDrawer.route.plus(Screen.NavigationDrawer.objectPath),
            arguments = listOf(navArgument(Screen.NavigationDrawer.objectName) {
                type = NavType.StringType
            })
        ) { backStack ->
            val typeId = backStack.arguments?.getString(Screen.NavigationDrawer.objectName)
            typeId?.let {
                TypePokemonScreen(
                    navController = navController, typeId
                )
            }
        }
        composable(
            Screen.PokemonDetail.route.plus(Screen.PokemonDetail.objectPath),
            arguments = listOf(navArgument(Screen.PokemonDetail.objectName) {
                type = NavType.IntType
            })
        ) {
            label = stringResource(R.string.pokemon_detail)
            val pokemonId = it.arguments?.getInt(Screen.PokemonDetail.objectName)
            pokemonId?.let {
                PokemonDetail(pokemonId)
            }
        }
    }
}

@Composable
fun navigationTitle(navController: NavController): String {
    return when (currentRoute(navController)) {
        Screen.PokemonDetail.route -> stringResource(id = R.string.pokemon_detail)
        else -> {
            ""
        }
    }
}

@Composable
fun currentRoute(navController: NavController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route?.substringBeforeLast("/")
}
