package com.fsacchi.hilt_mvvm_compose_pokedex.ui.screens.types

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.fsacchi.hilt_mvvm_compose_pokedex.ui.component.PokemonItemList

@Composable
fun TypePokemonScreen(
    navController: NavController,
    pokemonType: String
) {
    val typePokemonsViewModel = hiltViewModel<TypePokemonsViewModel>()
    PokemonItemList(
        navController = navController,
        pokemons = typePokemonsViewModel.pokemonsByType(pokemonType),
        null,
        null
    ){

    }
}