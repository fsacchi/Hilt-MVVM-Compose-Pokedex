package com.fsacchi.hilt_mvvm_compose_pokedex.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.fsacchi.hilt_mvvm_compose_pokedex.data.datasource.remote.ApiService
import com.fsacchi.hilt_mvvm_compose_pokedex.data.datasource.remote.paging.PokemonsPagingDataSource
import com.fsacchi.hilt_mvvm_compose_pokedex.data.model.ResultItem
import com.fsacchi.hilt_mvvm_compose_pokedex.data.model.TypesPokemon
import com.fsacchi.hilt_mvvm_compose_pokedex.data.model.pokemondetail.PokemonDetail
import com.fsacchi.hilt_mvvm_compose_pokedex.utils.network.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PokemonRepository @Inject constructor(
    private val apiService: ApiService
) : PokemonRepositoryInterface {
    override suspend fun pokemonDetail(pokemonId: Int): Flow<DataState<PokemonDetail>> = flow {
        emit(DataState.Loading)
        try {
            val searchResult = apiService.pokemonDetail(pokemonId)
            emit(DataState.Success(searchResult))

        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    override suspend fun typesList(): Flow<DataState<TypesPokemon>> = flow {
        emit(DataState.Loading)
        try {
            val genreResult = apiService.typeList()
            emit(DataState.Success(TypesPokemon(genreResult.results)))

        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    override fun pokemonPagingDataSource(typesPokemon: String?): Flow<PagingData<ResultItem>> = Pager(
        pagingSourceFactory = { PokemonsPagingDataSource(apiService, typesPokemon) },
        config = PagingConfig(pageSize = 1)
    ).flow
}