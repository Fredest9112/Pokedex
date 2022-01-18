package com.learningkotlin.pokedex.view

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.learningkotlin.pokedex.R
import com.learningkotlin.pokedex.databinding.PokemonEvolutionsItemBinding
import com.learningkotlin.pokedex.repository.database.Pokemon
import com.learningkotlin.pokedex.repository.utilities.Utilities

class RVEvolutionsPokemonAdapter(private val pokemonEvolutions: List<Pokemon>):
    RecyclerView.Adapter<EvolutionsViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EvolutionsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return EvolutionsViewHolder(layoutInflater.inflate(R.layout.pokemon_evolutions_item, parent, false))
    }

    override fun onBindViewHolder(holder: EvolutionsViewHolder, position: Int) {
        try{
            val evol = pokemonEvolutions[position]
            holder.bindEvolution(evol)
        } catch(e:NullPointerException){
            Log.e("NullOnRvEvolutions","${e.message}")
        }
    }

    override fun getItemCount(): Int {
        return pokemonEvolutions.size
    }

}

class EvolutionsViewHolder(view:View):RecyclerView.ViewHolder(view) {
    private val binding = PokemonEvolutionsItemBinding.bind(view)
    fun bindEvolution(evol:Pokemon) {
        binding.pokemonName.text = evol.name.replaceFirstChar { it.uppercase() }
        Utilities().bindPicture(evol.image, binding.pokemonImage)
    }
}
