package com.learningkotlin.pokedex.repository.api

import android.util.Log
import com.google.gson.GsonBuilder
import com.learningkotlin.pokedex.repository.constants.Constants.Companion.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
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

    fun getRetrofit():Retrofit{
        lateinit var info:Retrofit
        try{
            info = Retrofit.Builder().baseUrl(BASE_URL).client(client)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create())).build()
        } catch(e:IOException){
            Log.d("Error","$e")
        }
        return info
    }
}