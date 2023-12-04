package com.fsacchi.hilt_mvvm_compose_pokedex.di

import com.fsacchi.hilt_mvvm_compose_pokedex.data.datasource.remote.ApiService
import com.fsacchi.hilt_mvvm_compose_pokedex.data.repository.PokemonRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    /**
     * Provides RemoteDataRepository for access api service method
     */
    @Singleton
    @Provides
    fun providePokemonRepository(
        apiService: ApiService,
    ): PokemonRepository {
        return PokemonRepository(
            apiService
        )
    }

}