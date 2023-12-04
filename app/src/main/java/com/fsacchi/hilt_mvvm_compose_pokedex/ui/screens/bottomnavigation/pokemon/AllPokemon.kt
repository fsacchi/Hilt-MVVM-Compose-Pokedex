package com.fsacchi.hilt_mvvm_compose_pokedex.ui.screens.bottomnavigation.pokemon

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.fsacchi.hilt_mvvm_compose_pokedex.data.model.ResultItem
import com.fsacchi.hilt_mvvm_compose_pokedex.ui.component.PokemonItemList

@Composable
fun AllPokemon(
    navController: NavController,
    types: ArrayList<ResultItem>? = null,
) {
    val allPokemonViewModel = hiltViewModel<AllPokemonViewModel>()
    PokemonItemList(
        navController = navController,
        pokemons = allPokemonViewModel.allPokemons,
        types = types,
        selectedName = allPokemonViewModel.selectedType.value
    ){
        allPokemonViewModel.filterData.value = it?.name
        it?.let {
            allPokemonViewModel.selectedType.value = it
        }
    }
}