package com.learningkotlin.pokedex.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.learningkotlin.pokedex.R
import com.learningkotlin.pokedex.model.PokemonSingletonViewModel
import com.learningkotlin.pokedex.model.PokemonViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PokemonViewAct : AppCompatActivity() {

    private lateinit var pokemonViewModel: PokemonViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pokemonViewModel = PokemonSingletonViewModel.getInstance(applicationContext, this)!!

        CoroutineScope(Dispatchers.IO).launch {
            pokemonViewModel.savePokemonInfo()
        }
    }
}