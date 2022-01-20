package com.learningkotlin.pokedex.model

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.learningkotlin.pokedex.repository.database.PokemonDatabase
import com.learningkotlin.pokedex.repository.database.PokemonRepository
import com.learningkotlin.pokedex.view.PokemonViewAct

class PokemonSingletonViewModel {

    companion object{
        private var pokemonViewModel:PokemonViewModel? = null
        fun getInstance(context: Context, pokemonViewAct: PokemonViewAct) = synchronized(this){
            if(pokemonViewModel==null){
                val dao = PokemonDatabase.getInstance(context.applicationContext).pokemonDao
                val repository = PokemonRepository(dao)
                val factory = PokemonViewModelFactory(repository)
                pokemonViewModel = ViewModelProvider(pokemonViewAct, factory).get(PokemonViewModel::class.java)
            }
            pokemonViewModel
        }
    }
}