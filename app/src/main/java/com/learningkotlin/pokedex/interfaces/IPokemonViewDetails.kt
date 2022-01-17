package com.learningkotlin.pokedex.interfaces

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.learningkotlin.pokedex.repository.api.pokemonSpeciesEndPoint.PokemonSpecies
import com.learningkotlin.pokedex.repository.database.Pokemon

interface IPokemonViewDetails {
    fun getLifeCycleOwner(): LifecycleOwner
    fun showPokemonBasicInfo(pokemon: Pokemon)
    fun getContext(): Context?
    fun showAdditionalInfo(
        pokemonSpecies: PokemonSpecies,
        pokemonEvolutions: List<Pokemon>,
        damageRelationsType1: List<List<String>>,
        damageRelationsType2: List<List<String>>,
        abilityInfo: Map<String, String>
    )
}