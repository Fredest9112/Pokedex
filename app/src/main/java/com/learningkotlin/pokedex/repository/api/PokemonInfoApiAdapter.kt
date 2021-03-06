package com.learningkotlin.pokedex.repository.api

import com.google.gson.GsonBuilder
import com.learningkotlin.pokedex.repository.constants.Constants.Companion.BASE_URL
import com.learningkotlin.pokedex.repository.constants.Constants.Companion.BASE_URL_SPECIES
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class PokemonInfoApiAdapter {

    private val interceptor = HttpLoggingInterceptor().apply {
        this.level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder().apply {
        this.addInterceptor(interceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
    }.build()

    fun getPokemonInfoRetrofit():Retrofit{
        return Retrofit.Builder().baseUrl(BASE_URL).client(client)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create())).build()
    }

    fun getPokemonSpeciesInfo():Retrofit{
        return Retrofit.Builder().baseUrl(BASE_URL_SPECIES).addConverterFactory(
            GsonConverterFactory.create(GsonBuilder().create())).build()
    }

    fun getPokemonDetails():Retrofit{
        return Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(
            GsonConverterFactory.create(GsonBuilder().create())).build()
    }

    fun getDamageRelations(url:String):Retrofit{
        return Retrofit.Builder().baseUrl(url).addConverterFactory(
            GsonConverterFactory.create(GsonBuilder().create())).build()
    }

    fun getAbilityDesc(url:String):Retrofit{
        return Retrofit.Builder().baseUrl(url).addConverterFactory(
            GsonConverterFactory.create(GsonBuilder().create())).build()
    }

    fun getEvolutionChain(url: String):Retrofit{
        return Retrofit.Builder().baseUrl(url).addConverterFactory(
            GsonConverterFactory.create(GsonBuilder().create())).build()
    }
}