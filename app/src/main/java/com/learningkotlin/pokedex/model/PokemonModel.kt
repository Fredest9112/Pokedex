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
import com.learningkotlin.pokedex.repository.api.pokemonEvolChainEndPoint.EvolvesTo
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

    override fun loadPokemon(): LiveData<List<Pokemon>> {
        return liveData {
            repository.pokemon.collect {
                emit(it)
            }
        }
    }

    override fun loadPokemonByType(type: String): LiveData<List<Pokemon>> {
        return liveData {
            repository.getPokemonByType(type).collect {
                emit(it)
            }
        }
    }

    override fun loadPokemonByQuery(query: String): LiveData<List<Pokemon>> {
        return liveData {
            repository.getPokemonListByQuery(query).collect {
                emit(it)
            }
        }
    }

    override suspend fun getPokemonSpecies(query: String): PokemonSpecies {
        lateinit var pokemonInfo:PokemonSpecies
        val response = PokemonInfoApiAdapter().getPokemonSpeciesInfo().create(
            IPokemonInfoApiService::class.java
        ).getPokemonSpeciesEndPoint(query)
        val bodyResponse = response.body()
        if(response.isSuccessful && bodyResponse!=null){
            pokemonInfo = bodyResponse
        }
        return pokemonInfo
    }

    override suspend fun getPokemonDetails(query: String): PokemonDetails {
        lateinit var pokemonInfo:PokemonDetails
        val response = PokemonInfoApiAdapter().getPokemonDetails()
            .create(IPokemonInfoApiService::class.java).getPokemonInfoFromUrl(query)
        val bodyResponse = response.body()
        if(response.isSuccessful && bodyResponse!=null){
            pokemonInfo = bodyResponse
        }
        return pokemonInfo
    }

    override suspend fun getDamageRelations(url: String): List<List<String>> {
        lateinit var doubleDamageFromDeferred: Deferred<MutableList<String>>
        lateinit var halfDamageFromDeferred: Deferred<MutableList<String>>
        lateinit var noDamageFromDeferred: Deferred<MutableList<String>>
        lateinit var doubleDamageToDeferred: Deferred<MutableList<String>>
        lateinit var halfDamageToDeferred: Deferred<MutableList<String>>
        lateinit var noDamageToDeferred: Deferred<MutableList<String>>

        val response = PokemonInfoApiAdapter().getDamageRelations(url)
            .create(IPokemonInfoApiService::class.java).getPokemonDamageRelations("")
        val bodyResponse = response.body()
        if(response.isSuccessful && bodyResponse!=null){
            coroutineScope {
                doubleDamageFromDeferred = async(Dispatchers.IO) {
                    val damageRelatInfo = mutableListOf<String>()
                    for (type in bodyResponse.damageRelations.doubleDamageFrom) {
                        damageRelatInfo.add(type.name)
                    }
                    return@async damageRelatInfo
                }

                halfDamageFromDeferred = async(Dispatchers.IO) {
                    val damageRelatInfo = mutableListOf<String>()
                    for (type in bodyResponse.damageRelations.halfDamageFrom) {
                        damageRelatInfo.add(type.name)
                    }
                    return@async damageRelatInfo
                }

                noDamageFromDeferred = async(Dispatchers.IO) {
                    val damageRelatInfo = mutableListOf<String>()
                    for (type in bodyResponse.damageRelations.noDamageFrom) {
                        damageRelatInfo.add(type.name)
                    }
                    return@async damageRelatInfo
                }

                doubleDamageToDeferred = async(Dispatchers.IO) {
                    val damageRelatInfo = mutableListOf<String>()
                    for (type in bodyResponse.damageRelations.doubleDamageTo) {
                        damageRelatInfo.add(type.name)
                    }
                    return@async damageRelatInfo
                }

                halfDamageToDeferred = async(Dispatchers.IO) {
                    val damageRelatInfo = mutableListOf<String>()
                    for (type in bodyResponse.damageRelations.halfDamageTo) {
                        damageRelatInfo.add(type.name)
                    }
                    return@async damageRelatInfo
                }

                noDamageToDeferred = async(Dispatchers.IO) {
                    val damageRelatInfo = mutableListOf<String>()
                    for (type in bodyResponse.damageRelations.noDamageTo) {
                        damageRelatInfo.add(type.name)
                    }
                    return@async damageRelatInfo
                }
            }
        }
        return listOf(
            doubleDamageFromDeferred.await(),
            halfDamageFromDeferred.await(),
            noDamageFromDeferred.await(),
            doubleDamageToDeferred.await(),
            halfDamageToDeferred.await(),
            noDamageToDeferred.await()
        )
    }

    override suspend fun getPokemonAbilityDesc(abilities: List<Abilities>): Map<String, String> {
        val pokemonInfo = mutableMapOf<String, String>()
        for(ability in abilities){
            val response = PokemonInfoApiAdapter().getAbilityDesc(ability.ability.url)
                .create(IPokemonInfoApiService::class.java).getPokemonAbilitiesEndPoint("")
            val bodyResponse = response.body()
            if(response.isSuccessful && bodyResponse!=null){
                for(language in bodyResponse.flavorTextEntries){
                    if(language.language.name=="en"){
                        pokemonInfo[ability.ability.name] = language.flavorText
                        break
                    }
                }
            }
        }
        return pokemonInfo
    }

    override suspend fun getPokemonEvolutionChain(
        evolutionChain: EvolutionChain,
        evolChainInfo: MutableList<String>
    ): List<String> {
        val response = PokemonInfoApiAdapter().getEvolutionChain(evolutionChain.url)
            .create(IPokemonInfoApiService::class.java).getPokemonEvolChain("")
        val bodyResponse = response.body()
        if(response.isSuccessful && bodyResponse!=null){
            evolChainInfo.add(bodyResponse.chain.species.name)
            setEvols(bodyResponse.chain.evolvesTo, evolChainInfo)
        }
        return evolChainInfo
    }

    override suspend fun getPokemonEvolutions(pokemonEvolutionChain: List<String>): List<Pokemon> {
        val pokemonList = mutableListOf<Pokemon>()
        coroutineScope {
            launch(Dispatchers.IO) {
                for(pokemonName in pokemonEvolutionChain){
                    val pokemon = repository.getPokemonByQuery(pokemonName)
                    if(pokemon!=null){
                        pokemonList.add(pokemon)
                    } else{
                        break
                    }
                }
            }
        }
        Log.i("pokemonList","$pokemonList, $pokemonEvolutionChain")
        return pokemonList
    }

    private fun getListOfAbilities(abilities: List<Abilities>): List<String> {
        val listOfAbilities = mutableListOf<String>()
        for (url in abilities) {
            listOfAbilities.add(url.ability.url)
        }
        return listOfAbilities
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

    private fun setEvols(evolList: List<EvolvesTo>, evolChainInfo: MutableList<String>) {
        if(evolList.isNotEmpty()){
            for(item in evolList){
                evolChainInfo.add(item.species.name)
                setEvols(item.evolvesTo, evolChainInfo)
            }
        }
    }
}