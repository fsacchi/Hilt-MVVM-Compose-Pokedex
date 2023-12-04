package com.fsacchi.hilt_mvvm_compose_pokedex.data.model

import com.google.gson.annotations.SerializedName

data class PokemonsByTypeModel(
    @SerializedName("pokemon")
    val pokemon: List<PokemonsType>
)

fun PokemonsByTypeModel.toResultModel(): List<ResultItem> {
    val listResults = mutableListOf<ResultItem>()
    pokemon.forEach {
        listResults.add(ResultItem(
            name = it.pokemon.name,
            url = it.pokemon.url
        ))
    }
    return listResults
}

data class PokemonsType(
    val pokemon: Pokemon
)

data class Pokemon(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)
