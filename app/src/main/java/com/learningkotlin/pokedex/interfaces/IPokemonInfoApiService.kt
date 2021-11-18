package com.learningkotlin.pokedex.interfaces

import com.learningkotlin.pokedex.repository.api.PokemonDetails
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface IPokemonInfoApiService {
    @GET
    suspend fun getInfoOfPokemon(@Url url: String): Response<PokemonDetails>
}