package com.learningkotlin.pokedex.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.learningkotlin.pokedex.repository.database.PokemonRepository

class PokemonViewModelFactory(private val repository: PokemonRepository): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(PokemonViewModel::class.java)){
            return PokemonViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}