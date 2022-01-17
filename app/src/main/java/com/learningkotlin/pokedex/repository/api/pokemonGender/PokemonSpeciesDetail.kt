package com.learningkotlin.pokedex.repository.api.pokemonGender

import com.learningkotlin.pokedex.repository.api.pokemonGender.PokemonSpecies

data class PokemonSpeciesDetail(
    val pokemon_species: PokemonSpecies,
    val rate: Int
)