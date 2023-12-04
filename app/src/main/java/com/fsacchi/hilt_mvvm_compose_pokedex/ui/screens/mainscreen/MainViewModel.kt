package com.fsacchi.hilt_mvvm_compose_pokedex.ui.screens.mainscreen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fsacchi.hilt_mvvm_compose_pokedex.data.model.TypesPokemon
import com.fsacchi.hilt_mvvm_compose_pokedex.data.repository.PokemonRepository
import com.fsacchi.hilt_mvvm_compose_pokedex.utils.network.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repo: PokemonRepository) : ViewModel() {
    val types: MutableState<DataState<TypesPokemon>?> = mutableStateOf(null)

    fun typeList() {
        viewModelScope.launch {
            repo.typesList().onEach {
                types.value = it
            }.launchIn(viewModelScope)
        }
    }
}