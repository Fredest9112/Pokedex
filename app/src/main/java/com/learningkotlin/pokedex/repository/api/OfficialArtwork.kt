package com.learningkotlin.pokedex.repository.api

import com.google.gson.annotations.SerializedName

data class OfficialArtwork(
    @SerializedName("front_default") val frontDefault: String
)