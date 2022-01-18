package com.learningkotlin.pokedex.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.learningkotlin.pokedex.R
import com.learningkotlin.pokedex.databinding.PokemonSkillsItemBinding

class RVAbilitiesPokemonAdapter(private val pokemonAbilityInfo: Map<String, String>):
    RecyclerView.Adapter<AbilityViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbilityViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return AbilityViewHolder(layoutInflater.inflate(R.layout.pokemon_skills_item, parent, false))
    }

    override fun onBindViewHolder(holder: AbilityViewHolder, position: Int) {
        val item = getMapKeyFromIndex(pokemonAbilityInfo, position)
        holder.bindInfo(item, pokemonAbilityInfo)
    }

    override fun getItemCount(): Int {
        return pokemonAbilityInfo.size
    }

    private fun getMapKeyFromIndex(pokemonAbilityInfo: Map<String, String>, position: Int): String {
        lateinit var key:String
        for((index,entry) in pokemonAbilityInfo.entries.withIndex()){
            if(position == index){
                key = entry.key
            }
        }
        return key
    }
}

class AbilityViewHolder(view:View):RecyclerView.ViewHolder(view) {
    private val binding = PokemonSkillsItemBinding.bind(view)
    fun bindInfo(item: String, pokemonAbilityInfo: Map<String, String>) {
        binding.skillTitle.text = item.replace("\\s+".toRegex(), " ").lowercase()
            .replaceFirstChar { it.uppercase() }
        binding.skillDescription.text = pokemonAbilityInfo[item]!!.replace("\\s+".toRegex()," ")
            .lowercase().replaceFirstChar { it.uppercase() }
    }
}
