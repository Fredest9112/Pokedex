package com.learningkotlin.pokedex.interfaces

import androidx.lifecycle.LifecycleOwner
import com.learningkotlin.pokedex.repository.database.Pokemon

interface IPokemonView {
    fun showPokemon(pokemonInfo: List<Pokemon>)
    fun getLifeCycleOwner():LifecycleOwner
    fun showEmptySearchMessage()
}