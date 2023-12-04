package com.fsacchi.hilt_mvvm_compose_pokedex.data.model.pokemondetail

import com.google.gson.annotations.SerializedName

data class PokemonDetail(
    val id: Int,
    val name: String,
    val order: Int,
    @SerializedName("base_experience")
    val baseExperience: Int,
    val weight: Int,
    val height: Int,
    val types: List<TypesPokemon>,
    val sprites: SpritesPokemon
) {
    fun getNameImage(): String = "${id}.svg"
    fun getHeightFormatted() =  "${height / 10},${height % 10} m"
    fun getWeightFormatted() =  "${weight / 10},${weight % 10} kg"
}

data class TypesPokemon(
    val slot: Int,
    val type: TypePokemon
)

data class TypePokemon(
    val name: String
)

data class SpritesPokemon(
    @SerializedName("front_default")
    val frontDefault: String,
    @SerializedName("back_default")
    val backDefault: String,
    @SerializedName("front_shiny")
    val frontShiny: String,
    @SerializedName("back_shiny")
    val backShiny: String,
)