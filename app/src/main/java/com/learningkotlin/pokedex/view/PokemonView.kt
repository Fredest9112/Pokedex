package com.learningkotlin.pokedex.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.learningkotlin.pokedex.R

class PokemonView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}