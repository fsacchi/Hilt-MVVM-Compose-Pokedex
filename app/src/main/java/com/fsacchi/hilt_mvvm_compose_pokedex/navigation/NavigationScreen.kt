package com.fsacchi.hilt_mvvm_compose_pokedex.navigation

sealed class Screen(
    val route: String,
    val objectName: String = "",
    val objectPath: String = ""
) {
    object Home : Screen("home_screen")
    object NavigationDrawer :
        Screen("navigation_drawer", objectName = "typeId", objectPath = "/{typeId}")

    object PokemonDetail :
        Screen("pokemon_detail_screen", objectName = "pokemonItem", objectPath = "/{pokemonItem}")
}