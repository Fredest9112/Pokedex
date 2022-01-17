package com.learningkotlin.pokedex.repository.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon_data_table")
data class Pokemon(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "pokemon_autoId")
    val autoId:Int,

    @ColumnInfo(name = "pokemon_id")
    var id:Int,

    @ColumnInfo(name = "pokemon_name")
    var name:String,

    @ColumnInfo(name = "pokemon_type1")
    var type1:String,

    @ColumnInfo(name = "pokemon_type2")
    var type2:String,

    @ColumnInfo(name = "pokemon_image")
    var image:String,

    @ColumnInfo(name = "pokemon_height")
    var height:Double,

    @ColumnInfo(name = "pokemon_weight")
    var weight:Double,

    @ColumnInfo(name = "abilities_list")
    var abilitiesList: List<String>,

    @ColumnInfo(name = "pokemon_hp")
    var hp:Int,

    @ColumnInfo(name = "pokemon_attack")
    var attack:Int,

    @ColumnInfo(name = "pokemon_defense")
    var defense:Int,

    @ColumnInfo(name = "pokemon_spAttack")
    var spAttack:Int,

    @ColumnInfo(name = "pokemon_spDefense")
    var spDefense:Int,

    @ColumnInfo(name = "pokemon_speed")
    var speed:Int
)
