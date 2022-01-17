package com.learningkotlin.pokedex.repository.api.pokemonDetails

import com.google.gson.annotations.SerializedName

data class OfficialArtwork(
    @SerializedName("front_default") val frontDefault: String
)