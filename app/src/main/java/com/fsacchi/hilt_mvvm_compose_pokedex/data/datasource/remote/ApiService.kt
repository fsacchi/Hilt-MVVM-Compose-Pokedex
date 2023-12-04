package com.fsacchi.hilt_mvvm_compose_pokedex.data.datasource.remote

import com.fsacchi.hilt_mvvm_compose_pokedex.data.model.PokemonsByTypeModel
import com.fsacchi.hilt_mvvm_compose_pokedex.data.model.ResultModel
import com.fsacchi.hilt_mvvm_compose_pokedex.data.model.pokemondetail.PokemonDetail
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("pokemon")
    suspend fun pokemons(
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int
    ): ResultModel

    @GET("type")
    suspend fun typeList(): ResultModel

    @GET("type/{idType}")
    suspend fun pokemonsByType(
        @Path("idType") idType: String
    ): PokemonsByTypeModel

    @GET("pokemon/{pokemonId}")
    suspend fun pokemonDetail(
        @Path("pokemonId") pokemonId: Int
    ): PokemonDetail
}