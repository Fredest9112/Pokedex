package com.learningkotlin.pokedex.model

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.learningkotlin.pokedex.interfaces.IPokemonInfoApiService
import com.learningkotlin.pokedex.interfaces.IPokemonModel
import com.learningkotlin.pokedex.repository.api.PokemonInfoApiAdapter
import com.learningkotlin.pokedex.repository.constants.Constants.Companion.MAX_NUM_POKEMON
import com.learningkotlin.pokedex.repository.database.Pokemon
import com.learningkotlin.pokedex.repository.database.PokemonDatabase
import com.learningkotlin.pokedex.repository.database.PokemonRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import java.net.ConnectException

class PokemonModel(context: Context) : IPokemonModel {

    private val fragContext = context
    private val dao = PokemonDatabase.getInstance(context).pokemonDao
    private val repository = PokemonRepository(dao)

    override suspend fun savePokemonInfo() {

        try {
            while (getIndex() <= MAX_NUM_POKEMON && isInternetAvailable(fragContext)) {
                Log.i("Connectivity", "${isInternetAvailable(fragContext)}")
                val index = getIndex()
                val response =
                    PokemonInfoApiAdapter().getRetrofit().create(IPokemonInfoApiService::class.java)
                        .getInfoOfPokemon("$index")
                val infoOfPokemon = response.body()

                if (response.isSuccessful && infoOfPokemon != null) {

                    var types2 = ""
                    if (infoOfPokemon.types.size > 1) {
                        types2 = infoOfPokemon.types[1].type.name
                    }
                    val pokemon = Pokemon(
                        0, infoOfPokemon.id, infoOfPokemon.name,
                        infoOfPokemon.types[0].type.name, types2,
                        infoOfPokemon.sprites.other.officialArtwork.frontDefault
                    )

                    repository.insert(pokemon)

                } else {
                    Log.d("Error cycle", "${response.errorBody()}")
                    break
                }
            }
        } catch (e: ConnectException) {
            Log.i("Connect Error", "$e")
        }

    }

    private fun isInternetAvailable(fragContext: Context) =
        (fragContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).run {
            getNetworkCapabilities(activeNetwork)?.run {
                hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                        || hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                        || hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
            } ?: false
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

    override fun loadPokemon(): LiveData<List<Pokemon>> = liveData {
        repository.pokemon.collect {
            emit(it)
        }
    }

//    override fun loadPokemonByType(type: String): LiveData<List<Pokemon>> {
//        TODO("Not yet implemented")
//    }
//
//    override fun loadPokemonByQuery(query: String): LiveData<List<Pokemon>> {
//        TODO("Not yet implemented")
//    }
}