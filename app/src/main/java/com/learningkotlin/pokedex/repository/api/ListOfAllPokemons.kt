package com.learningkotlin.pokedex.repository.api

data class ListOfAllPokemons(
    val count: Int,
    val next: Any,
    val previous: Any,
    val results: List<Result>
)
