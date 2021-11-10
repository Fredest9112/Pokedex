package com.learningkotlin.pokedex.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.learningkotlin.pokedex.R
import com.learningkotlin.pokedex.databinding.ActivityMainBinding

class PokemonView : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.fragmentContainerView)
        binding.BNmenuHomeBytype.setupWithNavController(navController)

    }
}