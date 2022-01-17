package com.learningkotlin.pokedex.interfaces

import com.learningkotlin.pokedex.repository.api.pokemonAbilityDetails.PokemonAbilityDetails
import com.learningkotlin.pokedex.repository.api.pokemonDamageRelatEndPoint.PokemonDamageRelations
import com.learningkotlin.pokedex.repository.api.pokemonDetails.PokemonDetails
import com.learningkotlin.pokedex.repository.api.pokemonEvolChainEndPoint.PokemonEvolChain
import com.learningkotlin.pokedex.repository.api.pokemonSpeciesEndPoint.PokemonSpecies
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface IPokemonInfoApiService {
    @GET
    suspend fun getPokemonInfoFromUrl(@Url url: String): Response<PokemonDetails>

    @GET
    suspend fun getPokemonSpeciesEndPoint(@Url url:String): Response<PokemonSpecies>

    @GET
    suspend fun getPokemonDamageRelations(@Url url: String): Response<PokemonDamageRelations>

    @GET
    suspend fun getPokemonAbilitiesEndPoint(@Url url: String): Response<PokemonAbilityDetails>

    @GET
    suspend fun getPokemonEvolChain(@Url url: String): Response<PokemonEvolChain>
}