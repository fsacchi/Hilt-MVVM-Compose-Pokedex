package com.fsacchi.hilt_mvvm_compose_pokedex.ui.screens.bottomnavigation.pokemon


import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.fsacchi.hilt_mvvm_compose_pokedex.data.model.ResultItem

import com.fsacchi.hilt_mvvm_compose_pokedex.data.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class AllPokemonViewModel @Inject constructor(val repo: PokemonRepository) : ViewModel() {
    var selectedType: MutableState<ResultItem> =
        mutableStateOf(ResultItem("", ""))
    val filterData = MutableStateFlow<String?>(null)

    @OptIn(ExperimentalCoroutinesApi::class)
    val allPokemons = filterData.flatMapLatest {
        repo.pokemonPagingDataSource(it)
    }.cachedIn(viewModelScope)

}