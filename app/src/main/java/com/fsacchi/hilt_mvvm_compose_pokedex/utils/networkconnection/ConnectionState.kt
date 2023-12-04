package com.fsacchi.hilt_mvvm_compose_pokedex.utils.networkconnection

sealed class ConnectionState {
    object Available : ConnectionState()
    object Unavailable : ConnectionState()
}