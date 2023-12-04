package com.fsacchi.hilt_mvvm_compose_pokedex.data.datasource.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.fsacchi.hilt_mvvm_compose_pokedex.data.datasource.remote.ApiService
import com.fsacchi.hilt_mvvm_compose_pokedex.data.model.ResultItem
import com.fsacchi.hilt_mvvm_compose_pokedex.data.model.toResultModel
import com.fsacchi.hilt_mvvm_compose_pokedex.utils.AppConstant
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

class PokemonsPagingDataSource @Inject constructor(private val apiService: ApiService, private val typePokemon:String?) :
    PagingSource<Int, ResultItem>() {

    override fun getRefreshKey(state: PagingState<Int, ResultItem>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResultItem> {
        return try {
            // Pesquisa global
            if (typePokemon.isNullOrBlank() || typePokemon == AppConstant.DEFAULT_TYPE_ITEM) {
                val nextPage = params.key ?: 1
                val offset = if (nextPage > 1) nextPage else 0
                val pokemonList = apiService.pokemons(offset = (offset * 20))
                LoadResult.Page(
                    data = pokemonList.results,
                    prevKey = if (nextPage == 1) null else nextPage - 1,
                    nextKey =  if (pokemonList.results.isNotEmpty()) nextPage + 1 else  null
                )
            } else {
                //Pesquisa por Tipo
                val pokemonList = apiService.pokemonsByType(typePokemon)
                LoadResult.Page(
                    data = pokemonList.toResultModel(),
                    prevKey = null,
                    nextKey = null
                )
            }

        } catch (exception: IOException) {
            Timber.e("exception ${exception.message}")
            return LoadResult.Error(exception)
        } catch (httpException: HttpException) {
            Timber.e("httpException ${httpException.message}")
            return LoadResult.Error(httpException)
        }
    }
}