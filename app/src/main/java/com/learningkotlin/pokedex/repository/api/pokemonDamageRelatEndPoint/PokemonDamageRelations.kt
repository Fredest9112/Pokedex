package com.learningkotlin.pokedex.repository.api.pokemonDamageRelatEndPoint

import com.google.gson.annotations.SerializedName
import com.learningkotlin.pokedex.repository.api.pokemonDamageRelatEndPoint.DamageRelations

data class PokemonDamageRelations(
    @SerializedName("damage_relations") val damageRelations: DamageRelations
)