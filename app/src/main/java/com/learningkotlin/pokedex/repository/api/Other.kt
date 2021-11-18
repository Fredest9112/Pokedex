package com.learningkotlin.pokedex.repository.api

import com.google.gson.annotations.SerializedName

data class Other(
    val dream_world: DreamWorld,
    val home: Home,
    @SerializedName("official-artwork") val officialArtwork: OfficialArtwork
)