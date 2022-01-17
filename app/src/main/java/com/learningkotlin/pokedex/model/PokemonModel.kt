package com.learningkotlin.pokedex.model

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.learningkotlin.pokedex.interfaces.IPokemonInfoApiService
import com.learningkotlin.pokedex.interfaces.IPokemonModel
import com.learningkotlin.pokedex.repository.api.PokemonInfoApiAdapter
import com.learningkotlin.pokedex.repository.api.pokemonDetails.Abilities
import com.learningkotlin.pokedex.repository.api.pokemonDetails.PokemonDetails
import com.learningkotlin.pokedex.repository.api.pokemonSpeciesEndPoint.EvolutionChain
import com.learningkotlin.pokedex.repository.api.pokemonSpeciesEndPoint.PokemonSpecies
import com.learningkotlin.pokedex.repository.constants.Constants.Companion.MAX_NUM_POKEMON
import com.learningkotlin.pokedex.repository.database.Pokemon
import com.learningkotlin.pokedex.repository.database.PokemonDatabase
import com.learningkotlin.pokedex.repository.database.PokemonRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import java.net.ConnectException

class PokemonModel(context: Context) : IPokemonModel {
    companion object {
        private var instance: PokemonModel? = null
        fun getInstance(context: Context) = synchronized(this) {
            if (instance == null) {
                instance = PokemonModel(context.applicationContext)
            }
            instance
        }
    }

    private val dao = PokemonDatabase.getInstance(context).pokemonDao
    private val repository = PokemonRepository(dao)
    override suspend fun savePokemonInfo() {
        try {
            while (getIndex() <= MAX_NUM_POKEMON) {
                val index = getIndex()
                val response =
                    PokemonInfoApiAdapter().getPokemonInfoRetrofit()
                        .create(IPokemonInfoApiService::class.java)
                        .getPokemonInfoFromUrl("$index")
                val infoOfPokemon = response.body()

                if (response.isSuccessful && infoOfPokemon != null) {
                    var type2 = ""
                    if (infoOfPokemon.types.size > 1) {
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
        for (url in abilities) {
            listOfAbilities.add(url.ability.url)
        }
        return listOfAbilities
    }

    override fun loadPokemon(): LiveData<List<Pokemon>> {
        return liveData {
            repository.pokemon.collect {
                emit(it)
            }
        }
    }

    override fun loadPokemonByType(type: String): LiveData<List<Pokemon>> {
        TODO("Not yet implemented")
    }

    override fun loadPokemonByQuery(query: String): LiveData<List<Pokemon>> {
        TODO("Not yet implemented")
    }

    override suspend fun getPokemonSpecies(query: String): PokemonSpecies {
        TODO("Not yet implemented")
    }

    override suspend fun getPokemonDetails(query: String): PokemonDetails {
        TODO("Not yet implemented")
    }

    override suspend fun getDamageRelations(url: String): List<List<String>> {
        TODO("Not yet implemented")
    }

    override suspend fun getPokemonAbilityDesc(abilities: List<Abilities>): Map<String, String> {
        TODO("Not yet implemented")
    }

    override suspend fun getPokemonEvolutionChain(
        evolutionChain: EvolutionChain,
        evolChainInfo: MutableList<String>
    ): List<String> {
        TODO("Not yet implemented")
    }

    override suspend fun getPokemonEvolutions(pokemonEvolutionChain: List<String>): List<Pokemon> {
        TODO("Not yet implemented")
    }
}