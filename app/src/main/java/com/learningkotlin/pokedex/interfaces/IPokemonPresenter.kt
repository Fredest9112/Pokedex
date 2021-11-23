package com.learningkotlin.pokedex.interfaces

interface IPokemonPresenter {
    fun pokemonInfo()
    fun showPokemonInfo()
    fun showPokemonInfoByType(type:String)
    fun showPokemonByQuery(query:String?)
    fun cancelCurrentJob()
}