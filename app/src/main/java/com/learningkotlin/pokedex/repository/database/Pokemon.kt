package com.learningkotlin.pokedex.repository.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon_data_table")
data class Pokemon(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "pokemon_autoId")
    val autoId: Int,

    @ColumnInfo(name = "pokemon_id")
    var id: Int,

    @ColumnInfo(name = "pokemon_name")
    var name: String,

    @ColumnInfo(name = "pokemon_type1")
    var type1: String,

    @ColumnInfo(name = "pokemon_type2")
    var type2: String,

    @ColumnInfo(name = "pokemon_image")
    var image: String
)
