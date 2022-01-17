package com.learningkotlin.pokedex.repository.api.pokemonDetails

import com.google.gson.annotations.SerializedName
import com.learningkotlin.pokedex.repository.api.pokemonDetails.Home
import com.learningkotlin.pokedex.repository.api.pokemonDetails.OfficialArtwork

data class Other(
    val home: Home,
    @SerializedName("official-artwork") val officialArtwork: OfficialArtwork
)