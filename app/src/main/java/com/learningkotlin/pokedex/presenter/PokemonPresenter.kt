package com.learningkotlin.pokedex.presenter

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.learningkotlin.pokedex.interfaces.*
import com.learningkotlin.pokedex.model.PokemonModel
import com.learningkotlin.pokedex.repository.database.Pokemon
import com.learningkotlin.pokedex.repository.utilities.ConnectivityStatus
import com.learningkotlin.pokedex.view.PokemonByTypeFragment
import com.learningkotlin.pokedex.view.PokemonDetailsFragment
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

    constructor(pokemonViewDetails: PokemonDetailsFragment, pokemonModel: PokemonModel) : this(){
        this.iPokemonViewDetails = pokemonViewDetails
        this.iPokemonModel = pokemonModel
    }

    override fun pokemonInfo() {
        job = SupervisorJob()
        val connectivity = iPokemonViewAct?.getContext()?.let { ConnectivityStatus(it) }
        iPokemonViewAct?.getLifeCycleOwner()?.let {
            connectivity?.observe(it) { isConnected ->
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
            }
        }
    }

    override fun showPokemonInfo() {
        if (pokemonInfo != null || pokemonInfo?.hasActiveObservers() == true) {
            pokemonInfo?.removeObservers(iPokemonView.getLifeCycleOwner())
            pokemonInfo = iPokemonModel.loadPokemon()
            pokemonInfo?.observe(iPokemonView.getLifeCycleOwner()) {
                iPokemonView.showPokemon(it)
            }
        } else {
            pokemonInfo = iPokemonModel.loadPokemon()
            pokemonInfo?.observe(iPokemonView.getLifeCycleOwner()) {
                iPokemonView.showPokemon(it)
            }
        }
    }

    override fun showPokemonInfoByType(type: String) {
        pokemonInfo = iPokemonModel.loadPokemonByType(type.lowercase())
        pokemonInfo?.observe(iPokemonView.getLifeCycleOwner()) {
            iPokemonView.showPokemon(it)
        }
    }

    override fun showPokemonByQuery(query: String) {
        pokemonInfo?.removeObservers(iPokemonView.getLifeCycleOwner())
        val queryInput = MutableLiveData(query)
        pokemonInfo = Transformations.switchMap(queryInput){
            iPokemonModel.loadPokemonByQuery(it)
        }
        pokemonInfo?.observe(iPokemonView.getLifeCycleOwner()) {
            if (it.isEmpty()) {
                iPokemonView.showEmptySearchMessage()
                iPokemonView.showPokemon(it)
            } else {
                iPokemonView.showPokemon(it)
            }
        }
    }

    override fun showPokemonDetails(query: String) {
        iPokemonViewDetails?.getLifeCycleOwner()?.let { pokemonInfo?.removeObservers(it) }
        pokemonInfo = iPokemonModel.loadPokemonByQuery(query.lowercase())
        iPokemonViewDetails?.getLifeCycleOwner()?.let { it ->
            pokemonInfo?.observe(it) { pokemonList ->
                iPokemonViewDetails?.showPokemonBasicInfo(pokemonList[0])
            }
        }
        job = SupervisorJob()
        val connectivity = iPokemonViewDetails?.getContext()?.let { ConnectivityStatus(it) }
        iPokemonViewDetails?.getLifeCycleOwner()?.let {
            connectivity?.observe(it) { isConnected ->
                try {
                    if (isConnected) {
                        job = CoroutineScope(Dispatchers.Main).launch {
                            Log.i("pokemonDetails", "Internet")
                            val pokemonSpeciesDef = async {
                                iPokemonModel.getPokemonSpecies(query)
                            }
                            val pokemonDetailsDef = async {
                                iPokemonModel.getPokemonDetails(query)
                            }

                            val pokemonSpecies = pokemonSpeciesDef.await()
                            val pokemonDetails = pokemonDetailsDef.await()

                            val damageRelationsType1Def = async {
                                iPokemonModel.getDamageRelations(
                                    pokemonDetails.types[0]
                                        .type.url
                                )
                            }

                            val damageRelationsType2Def = async {
                                if (pokemonDetailsDef.await().types.size > 1) {
                                    iPokemonModel.getDamageRelations(
                                        pokemonDetails.types[1]
                                            .type.url
                                    )
                                } else {
                                    emptyList()
                                }
                            }

                            val abilityDef = async {
                                iPokemonModel.getPokemonAbilityDesc(pokemonDetails.abilities)
                            }

                            val evolutionChainDef = async {
                                val evolChainInfo = mutableListOf<String>()
                                iPokemonModel.getPokemonEvolutionChain(
                                    pokemonSpecies.evolutionChain, evolChainInfo
                                )
                            }
                            val pokemonEvolutionChain = evolutionChainDef.await()

                            val pokemonEvolutions = iPokemonModel.getPokemonEvolutions(
                                pokemonEvolutionChain
                            )
                            iPokemonViewDetails!!.showAdditionalInfo(
                                pokemonSpecies, pokemonEvolutions,
                                damageRelationsType1Def.await(),
                                damageRelationsType2Def.await(),
                                abilityDef.await()
                            )
                        }
                    } else {
                        job.cancel()
                        Log.i("pokemonDetails", "NoInternet, Job Cancelled")
                    }
                } catch (e: Exception) {
                    e.message
                }
            }
        }
    }
}