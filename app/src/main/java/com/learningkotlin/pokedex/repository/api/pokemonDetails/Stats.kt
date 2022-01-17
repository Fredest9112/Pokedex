package com.learningkotlin.pokedex.repository.api.pokemonDetails

import com.google.gson.annotations.SerializedName
import com.learningkotlin.pokedex.repository.api.pokemonDetails.Stat

data class Stats(
    @SerializedName("base_stat") val baseStat: Int,
    val stat: Stat
)
