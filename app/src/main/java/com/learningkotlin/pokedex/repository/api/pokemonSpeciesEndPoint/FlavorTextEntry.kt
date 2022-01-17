package com.learningkotlin.pokedex.repository.api.pokemonSpeciesEndPoint

import com.google.gson.annotations.SerializedName

data class FlavorTextEntry(
    @SerializedName("flavor_text") val flavorText: String,
    val language: Language
)