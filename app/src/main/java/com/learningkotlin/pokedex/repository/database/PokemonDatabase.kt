package com.learningkotlin.pokedex.repository.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Pokemon::class], version = 1)
@TypeConverters(Converters::class)
abstract class PokemonDatabase : RoomDatabase() {
    abstract val pokemonDao: PokemonDao

    companion object {
        @Volatile
        private var INSTANCE: PokemonDatabase? = null
        fun getInstance(context: Context): PokemonDatabase {
            synchronized(this) {
                var instance: PokemonDatabase? = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context,
                        PokemonDatabase::class.java,
                        "pokemon_data_table"
                    ).build()
                }
                return instance
            }
        }
    }
}