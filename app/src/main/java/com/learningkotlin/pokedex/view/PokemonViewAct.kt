package com.learningkotlin.pokedex.view

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import com.learningkotlin.pokedex.R
import com.learningkotlin.pokedex.interfaces.IPokemonPresenter
import com.learningkotlin.pokedex.interfaces.IPokemonViewAct
import com.learningkotlin.pokedex.model.PokemonModel
import com.learningkotlin.pokedex.presenter.PokemonPresenter

class PokemonViewAct : AppCompatActivity(), IPokemonViewAct {

    private lateinit var iPokemonPresenter: IPokemonPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        iPokemonPresenter = PokemonPresenter(this, PokemonModel.getInstance(applicationContext)!!)
        iPokemonPresenter.pokemonInfo()
    }

    override fun getLifeCycleOwner(): LifecycleOwner {
        return this
    }

    override fun getContext(): Context {
        return applicationContext
    }
}