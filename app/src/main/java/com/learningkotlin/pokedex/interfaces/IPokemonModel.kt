package com.learningkotlin.pokedex.interfaces

import androidx.lifecycle.LiveData
import com.learningkotlin.pokedex.repository.database.Pokemon

interface IPokemonModel {
    suspend fun savePokemonInfo()
    fun loadPokemon(): LiveData<List<Pokemon>>
}