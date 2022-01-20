package com.learningkotlin.pokedex.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.learningkotlin.pokedex.repository.api.IPokemonInfoApiService
import com.learningkotlin.pokedex.repository.api.PokemonInfoApiAdapter
import com.learningkotlin.pokedex.repository.api.pokemonDetails.Abilities
import com.learningkotlin.pokedex.repository.constants.Constants.Companion.MAX_NUM_POKEMON
import com.learningkotlin.pokedex.repository.database.Pokemon
import com.learningkotlin.pokedex.repository.database.PokemonRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import java.net.ConnectException

class PokemonViewModel(private val repository: PokemonRepository) : ViewModel() {

    suspend fun savePokemonInfo() {

        try {
            while (getIndex() <= MAX_NUM_POKEMON) {
                val index = getIndex()
                val response =
                    PokemonInfoApiAdapter().getPokemonInfoRetrofit().create(IPokemonInfoApiService::class.java)
                        .getPokemonInfoFromUrl("$index")
                val infoOfPokemon = response.body()

                if (response.isSuccessful && infoOfPokemon != null) {
                    var type2 = ""
                    if(infoOfPokemon.types.size>1){
                        type2 = infoOfPokemon.types[1].type.name
                    }

                    val pokemon = Pokemon(
                        0, infoOfPokemon.id, infoOfPokemon.name,
                        infoOfPokemon.types[0].type.name, type2,
                        infoOfPokemon.sprites.other.officialArtwork.frontDefault,
                        infoOfPokemon.height.div(10), infoOfPokemon.weight.div(10),
                        getListOfAbilities(infoOfPokemon.abilities),
                        infoOfPokemon.stats[0].baseStat, infoOfPokemon.stats[1].baseStat,
                        infoOfPokemon.stats[2].baseStat, infoOfPokemon.stats[3].baseStat,
                        infoOfPokemon.stats[4].baseStat, infoOfPokemon.stats[5].baseStat
                    )
                    repository.insert(pokemon)
                } else {
                    Log.d("Error cycle", "${response.errorBody()}")
                    break
                }
            }
        } catch (e: ConnectException) {
            Log.i("Connectivity Error", "$e")
        }
    }

    private suspend fun getIndex(): Int {
        lateinit var deferredIndex: Deferred<Int>
        coroutineScope {
            launch(Dispatchers.IO) {
                deferredIndex = async {
                    return@async repository.getRowCount()
                }
            }
        }
        return deferredIndex.await() + 1
    }

    private fun getListOfAbilities(abilities: List<Abilities>): List<String> {
        val listOfAbilities = mutableListOf<String>()
        for(url in abilities){
            listOfAbilities.add(url.ability.url)
        }
        return listOfAbilities
    }

    fun loadPokemon():LiveData<List<Pokemon>>{
        return liveData {
            repository.pokemon.collect {
                emit(it)
            }
        }
    }

}