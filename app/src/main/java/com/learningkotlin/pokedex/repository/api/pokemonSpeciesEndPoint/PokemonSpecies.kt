package com.learningkotlin.pokedex.repository.api.pokemonSpeciesEndPoint

import com.google.gson.annotations.SerializedName
import com.learningkotlin.pokedex.repository.api.pokemonSpeciesEndPoint.EvolutionChain
import com.learningkotlin.pokedex.repository.api.pokemonSpeciesEndPoint.FlavorTextEntry
import com.learningkotlin.pokedex.repository.api.pokemonSpeciesEndPoint.Genera

data class PokemonSpecies(
    @SerializedName("evolution_chain") val evolutionChain: EvolutionChain,
    @SerializedName("flavor_text_entries") val flavorTextEntries: List<FlavorTextEntry>,
    val genera: List<Genera>,
)