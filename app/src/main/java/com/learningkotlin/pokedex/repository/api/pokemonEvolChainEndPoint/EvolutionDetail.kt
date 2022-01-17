package com.learningkotlin.pokedex.repository.api.pokemonEvolChainEndPoint

import com.google.gson.annotations.SerializedName

data class EvolutionDetail(
    val gender: Any,
    @SerializedName("held_item") val heldItem: Any,
    val item: Any,
    @SerializedName("known_move") val knownMove: Any,
    @SerializedName("known_move_type") val knownMoveType: Any,
    val location: Any,
    @SerializedName("min_affection") val minAffection: Any,
    @SerializedName("min_beauty") val minBeauty: Any,
    @SerializedName("min_happiness") val min_happiness: Any,
    @SerializedName("min_level") val minLevel: Int,
    @SerializedName("needs_overworld_rain") val needsOverworldRain: Boolean,
    @SerializedName("party_species") val partySpecies: Any,
    @SerializedName("party_type") val partyType: Any,
    @SerializedName("relative_physical_stats") val relativePhysicalStats: Any,
    @SerializedName("time_of_day") val timeOfDay: String,
    @SerializedName("trade_species") val tradeSpecies: Any,
    @SerializedName("turn_upside_down") val turnUpsideDown: Boolean
)