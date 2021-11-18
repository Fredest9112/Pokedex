package com.learningkotlin.pokedex.repository.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonDao {

    @Insert
    suspend fun insertPokemon(pokemon: Pokemon)

    @Query("SELECT * FROM pokemon_data_table")
    fun getAllPokemon():Flow<List<Pokemon>>

    @Query("SELECT COUNT(pokemon_autoId) FROM pokemon_data_table")
    suspend fun getRowCount():Int

    @Query("SELECT * FROM pokemon_data_table WHERE pokemon_type1 IN (:type) OR pokemon_type2 IN (:type)")
    fun getPokemonByType(type:String):Flow<List<Pokemon>>

    @Query("SELECT * FROM pokemon_data_table WHERE pokemon_name IN (:query) OR pokemon_id IN (:query)")
    fun getPokemonByQuery(query: String):Flow<List<Pokemon>>
}