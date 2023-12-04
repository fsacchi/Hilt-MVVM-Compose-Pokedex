package com.fsacchi.hilt_mvvm_compose_pokedex.ui.screens.detail

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fsacchi.hilt_mvvm_compose_pokedex.data.model.pokemondetail.PokemonDetail
import com.fsacchi.hilt_mvvm_compose_pokedex.data.repository.PokemonRepository
import com.fsacchi.hilt_mvvm_compose_pokedex.utils.network.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(private val repo: PokemonRepository) : ViewModel() {
    val pokemonDetail: MutableState<DataState<PokemonDetail>?> = mutableStateOf(null)

    fun pokemonDetailApi(pokemonId: Int) {
        viewModelScope.launch {
            repo.pokemonDetail(pokemonId).onEach {
                pokemonDetail.value = it
            }.launchIn(viewModelScope)
        }
    }
}