package com.learningkotlin.pokedex.repository.api.pokemonEvolChainEndPoint

import com.google.gson.annotations.SerializedName

data class EvolvesTo(
    @SerializedName("evolution_details") val evolutionDetails: List<EvolutionDetail>,
    @SerializedName("evolves_to") val evolvesTo: List<EvolvesTo>,
    val species: Species
)