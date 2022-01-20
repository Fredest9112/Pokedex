package com.learningkotlin.pokedex.model

import android.content.Context
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.learningkotlin.pokedex.repository.database.PokemonDatabase
import com.learningkotlin.pokedex.repository.database.PokemonRepository

class PokemonSingletonViewModel {

    companion object{
        private var pokemonViewModel:PokemonViewModel? = null
        fun getInstance(context: Context, requireActivity: FragmentActivity) = synchronized(this){
            if(pokemonViewModel==null){
                val dao = PokemonDatabase.getInstance(context.applicationContext).pokemonDao
                val repository = PokemonRepository(dao)
                val factory = PokemonViewModelFactory(repository)
                pokemonViewModel = ViewModelProvider(requireActivity, factory)[PokemonViewModel::class.java]
            }
            pokemonViewModel
        }
    }
}