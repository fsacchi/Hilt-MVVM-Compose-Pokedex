package com.fsacchi.hilt_mvvm_compose_pokedex.data.repository

import androidx.paging.PagingData
import com.fsacchi.hilt_mvvm_compose_pokedex.data.model.ResultItem
import com.fsacchi.hilt_mvvm_compose_pokedex.data.model.TypesPokemon
import com.fsacchi.hilt_mvvm_compose_pokedex.data.model.pokemondetail.PokemonDetail
import com.fsacchi.hilt_mvvm_compose_pokedex.utils.network.DataState
import kotlinx.coroutines.flow.Flow

interface PokemonRepositoryInterface {
    suspend fun pokemonDetail(pokemonId: Int): Flow<DataState<PokemonDetail>>
    suspend fun typesList(): Flow<DataState<TypesPokemon>>
    fun pokemonPagingDataSource(typesPokemon: String?): Flow<PagingData<ResultItem>>
}