package com.learningkotlin.pokedex.repository.database

import kotlinx.coroutines.flow.Flow

class PokemonRepository(private val dao: PokemonDao) {

    val pokemon = dao.getAllPokemon()

    suspend fun insert(pokemon: Pokemon) {
        dao.insertPokemon(pokemon)
    }

    suspend fun getRowCount(): Int {
        return dao.getRowCount()
    }

    fun getPokemonByType(type: String): Flow<List<Pokemon>> {
        return dao.getPokemonByType(type)
    }

    fun getPokemonListByQuery(query: String): Flow<List<Pokemon>> {
        return dao.getPokemonByQuery(query)
    }

    fun getPokemonByQuery(query: String): Pokemon? {
        return dao.getPokemonImagesByQuery(query)
    }
}