package com.fsacchi.hilt_mvvm_compose_pokedex.ui.screens.types

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.fsacchi.hilt_mvvm_compose_pokedex.data.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TypePokemonsViewModel @Inject constructor(private val repo: PokemonRepository) : ViewModel() {
    fun pokemonsByType(pokemonType: String) =
        repo.pokemonPagingDataSource(pokemonType).cachedIn(viewModelScope)
}