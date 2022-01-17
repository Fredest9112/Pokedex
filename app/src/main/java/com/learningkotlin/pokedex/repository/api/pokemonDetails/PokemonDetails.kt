package com.learningkotlin.pokedex.repository.api.pokemonDetails

data class PokemonDetails(
    val name: String,
    val id: Int,
    val types: List<Types>,
    val sprites: Sprites,
    val height:Double,
    val weight:Double,
    val abilities:List<Abilities>,
    val stats:List<Stats>,
    val species: Species
)
