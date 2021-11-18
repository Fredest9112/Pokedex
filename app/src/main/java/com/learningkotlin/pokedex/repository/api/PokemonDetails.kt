package com.learningkotlin.pokedex.repository.api

data class PokemonDetails(
    val name: String,
    val id: Int,
    val types: List<Types>,
    val sprites: Sprites
)
