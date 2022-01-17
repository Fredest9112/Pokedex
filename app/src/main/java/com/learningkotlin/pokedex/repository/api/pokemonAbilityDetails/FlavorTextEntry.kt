package com.learningkotlin.pokedex.repository.api.pokemonAbilityDetails

import com.google.gson.annotations.SerializedName
import com.learningkotlin.pokedex.repository.api.pokemonAbilityDetails.Language

data class FlavorTextEntry(
    @SerializedName("flavor_text") val flavorText: String,
    val language: Language
)