package com.learningkotlin.pokedex.interfaces

import androidx.lifecycle.LiveData
import com.learningkotlin.pokedex.repository.api.pokemonDetails.Abilities
import com.learningkotlin.pokedex.repository.api.pokemonDetails.PokemonDetails
import com.learningkotlin.pokedex.repository.api.pokemonSpeciesEndPoint.EvolutionChain
import com.learningkotlin.pokedex.repository.api.pokemonSpeciesEndPoint.PokemonSpecies
import com.learningkotlin.pokedex.repository.database.Pokemon

interface IPokemonModel {
    suspend fun savePokemonInfo()
    fun loadPokemon(): LiveData<List<Pokemon>>
    fun loadPokemonByType(type:String): LiveData<List<Pokemon>>
    fun loadPokemonByQuery(query:String): LiveData<List<Pokemon>>
    suspend fun getPokemonSpecies(query: String): PokemonSpecies
    suspend fun getPokemonDetails(query: String): PokemonDetails
    suspend fun getDamageRelations(url:String):List<List<String>>
    suspend fun getPokemonAbilityDesc(abilities: List<Abilities>):Map<String, String>
    suspend fun getPokemonEvolutionChain(evolutionChain: EvolutionChain,
                                         evolChainInfo:MutableList<String>):List<String>
    suspend fun getPokemonEvolutions(pokemonEvolutionChain: List<String>): List<Pokemon>
}