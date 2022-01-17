package com.learningkotlin.pokedex.repository.api.pokemonGender

data class PokemonGender(
    val id: Int,
    val name: String,
    val pokemon_species_details: List<PokemonSpeciesDetail>,
)