package com.learningkotlin.pokedex.repository.api.pokemonAbilityDetails

import com.google.gson.annotations.SerializedName
import com.learningkotlin.pokedex.repository.api.pokemonAbilityDetails.FlavorTextEntry

data class PokemonAbilityDetails(
    @SerializedName("flavor_text_entries") val flavorTextEntries: List<FlavorTextEntry>
)