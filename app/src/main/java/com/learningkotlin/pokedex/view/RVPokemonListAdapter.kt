package com.learningkotlin.pokedex.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.learningkotlin.pokedex.R
import com.learningkotlin.pokedex.databinding.PokemonListItemBinding
import com.learningkotlin.pokedex.repository.database.Pokemon
import com.learningkotlin.pokedex.repository.utilities.Utilities

class RVPokemonListAdapter: RecyclerView.Adapter<PokemonListViewHolder>() {
    private val pokemonList = mutableListOf<Pokemon>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PokemonListViewHolder(layoutInflater.inflate(R.layout.pokemon_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: PokemonListViewHolder, position: Int) {
        val item = pokemonList[position]
        holder.bindInfo(item, holder.itemView.context)
    }

    override fun getItemCount(): Int {
        return pokemonList.size
    }

    fun addPokemon(pokemonInfo: List<Pokemon>){
        pokemonList.clear()
        pokemonList.addAll(pokemonInfo)
        notifyDataSetChanged()
    }
}

class PokemonListViewHolder(view: View): RecyclerView.ViewHolder(view){
    private val binding = PokemonListItemBinding.bind(view)
    fun bindInfo(item: Pokemon, context: Context?) {
        binding.cardBackGroundType2.visibility = View.INVISIBLE
        binding.pokemonId.text = item.id.toString()
        binding.pokemonName.text = item.name.replaceFirstChar { it.uppercase() }
        binding.pokemonDetailsType1.text = item.type1.replaceFirstChar { it.uppercase() }
        Utilities().setBackGroundColorOnType(binding.cardBackgroundType1, context, item.type1)
        Utilities().bindPicture(item.image, binding.pokemonImage)
        if(item.type2.isNotEmpty()){
            binding.cardBackGroundType2.visibility = View.VISIBLE
            binding.pokemonDetailsType2.text = item.type2.replaceFirstChar { it.uppercase() }
            Utilities().setBackGroundColorOnType(binding.cardBackGroundType2, context, item.type2)
        }
    }

}
