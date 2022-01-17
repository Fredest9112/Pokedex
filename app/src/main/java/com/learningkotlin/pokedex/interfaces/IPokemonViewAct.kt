package com.learningkotlin.pokedex.interfaces

import android.content.Context
import androidx.lifecycle.LifecycleOwner

interface IPokemonViewAct {
    fun getLifeCycleOwner():LifecycleOwner
    fun getContext():Context
}