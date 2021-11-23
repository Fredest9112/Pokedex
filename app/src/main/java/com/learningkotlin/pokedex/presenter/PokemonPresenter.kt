package com.learningkotlin.pokedex.presenter

import android.util.Log
import androidx.lifecycle.LiveData
import com.learningkotlin.pokedex.interfaces.IPokemonModel
import com.learningkotlin.pokedex.interfaces.IPokemonPresenter
import com.learningkotlin.pokedex.interfaces.IPokemonView
import com.learningkotlin.pokedex.repository.database.Pokemon
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class PokemonPresenter(
    private val iPokemonView: IPokemonView,
    private val iPokemonModel: IPokemonModel
) : IPokemonPresenter {
    private lateinit var job: Job
    private lateinit var pokemonInfo: LiveData<List<Pokemon>>
    private var pokemonInfoByQuery: LiveData<List<Pokemon>>? = null
    override fun pokemonInfo() {
        job = Job()
        Log.i("Job","Started")
        job = CoroutineScope(Dispatchers.IO).launch {
            iPokemonModel.savePokemonInfo()
        }
    }

    override fun showPokemonInfo() {
        if(pokemonInfoByQuery!=null){
            pokemonInfoByQuery!!.removeObservers(iPokemonView.getLifeCycleOwner())
            pokemonInfo.observe(iPokemonView.getLifeCycleOwner(),{
                iPokemonView.showPokemon(it)
            })
        } else{
            pokemonInfo = iPokemonModel.loadPokemon()
            pokemonInfo.observe(iPokemonView.getLifeCycleOwner(),{
                iPokemonView.showPokemon(it)
            })
        }
    }

    override fun showPokemonInfoByType(type: String) {

    }

    override fun showPokemonByQuery(query: String?) {

    }

    override fun cancelCurrentJob() {
        Log.i("Job","Cancelled")
        job.cancel()
    }
}