package com.learningkotlin.pokedex.repository.api

import com.google.gson.annotations.SerializedName

data class Home(
    @SerializedName("front_default") val frontDefault:String
    //pokemon No. 718 is null for this picture, that causes a crash on db
)
