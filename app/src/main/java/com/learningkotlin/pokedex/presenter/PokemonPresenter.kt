package com.learningkotlin.pokedex.presenter

import android.util.Log
import androidx.lifecycle.LiveData
import com.learningkotlin.pokedex.interfaces.*
import com.learningkotlin.pokedex.model.PokemonModel
import com.learningkotlin.pokedex.repository.database.Pokemon
import com.learningkotlin.pokedex.repository.utilities.ConnectivityStatus
import com.learningkotlin.pokedex.view.PokemonByTypeFragment
import com.learningkotlin.pokedex.view.PokemonListFragment
import com.learningkotlin.pokedex.view.PokemonViewAct
import kotlinx.coroutines.*

class PokemonPresenter() : IPokemonPresenter {

    private lateinit var iPokemonView: IPokemonView
    private var iPokemonViewAct: IPokemonViewAct? = null
    private var iPokemonViewDetails: IPokemonViewDetails? = null
    private lateinit var iPokemonModel: IPokemonModel
    private lateinit var job: Job
    private var pokemonInfo: LiveData<List<Pokemon>>? = null

    constructor(PokemonViewAct: PokemonViewAct, pokemonModel: PokemonModel) : this() {
        this.iPokemonViewAct = PokemonViewAct
        this.iPokemonModel = pokemonModel
    }

    constructor(PokemonListFragment: PokemonListFragment, pokemonModel: PokemonModel) : this() {
        this.iPokemonView = PokemonListFragment
        this.iPokemonModel = pokemonModel
    }

    constructor(pokemonByTypeFragment: PokemonByTypeFragment, pokemonModel: PokemonModel) : this() {
        this.iPokemonView = pokemonByTypeFragment
        this.iPokemonModel = pokemonModel
    }

    override fun pokemonInfo() {
        job = SupervisorJob()
        val connectivity = iPokemonViewAct?.getContext()?.let { ConnectivityStatus(it) }
        iPokemonViewAct?.getLifeCycleOwner()?.let {
            connectivity?.observe(it, { isConnected ->
                try {
                    if (isConnected) {
                        job = CoroutineScope(Dispatchers.IO).launch {
                            Log.i("pokemonInfo", "Internet")
                            iPokemonModel.savePokemonInfo()
                        }
                    } else {
                        Log.i("pokemonInfo", "NoInternet, Job Cancelled")
                    }
                } catch (e: Exception) {
                    e.message
                }
            })
        }
    }

    override fun showPokemonInfo() {
        if (pokemonInfo != null || pokemonInfo?.hasActiveObservers() == true) {
            pokemonInfo?.removeObservers(iPokemonView.getLifeCycleOwner())
            pokemonInfo = iPokemonModel.loadPokemon()
            pokemonInfo?.observe(iPokemonView.getLifeCycleOwner(), {
                iPokemonView.showPokemon(it)
            })
        } else {
            pokemonInfo = iPokemonModel.loadPokemon()
            pokemonInfo?.observe(iPokemonView.getLifeCycleOwner(), {
                iPokemonView.showPokemon(it)
            })
        }
    }

    override fun showPokemonInfoByType(type: String) {
        pokemonInfo = iPokemonModel.loadPokemonByType(type.lowercase())
        pokemonInfo?.observe(iPokemonView.getLifeCycleOwner(), {
            iPokemonView.showPokemon(it)
        })
    }

    override fun showPokemonByQuery(query: String) {
        TODO("Not yet implemented")
    }

    override fun showPokemonDetails(query: String) {
        TODO("Not yet implemented")
    }
}