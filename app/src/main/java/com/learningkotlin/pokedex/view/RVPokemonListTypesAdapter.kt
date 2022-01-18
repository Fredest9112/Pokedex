package com.learningkotlin.pokedex.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.learningkotlin.pokedex.R
import com.learningkotlin.pokedex.databinding.PokemonListbytypeItemBinding
import com.learningkotlin.pokedex.repository.utilities.Utilities

class RVPokemonListTypesAdapter(private val types: List<String>) :
    RecyclerView.Adapter<PokemonListTypesViewHolder>() {

    private lateinit var onItemClickListener:OnItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonListTypesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PokemonListTypesViewHolder(layoutInflater.inflate(
            R.layout.pokemon_listbytype_item, parent, false), onItemClickListener)
    }

    override fun onBindViewHolder(holder: PokemonListTypesViewHolder, position: Int) {
        val type = types[position]
        holder.bindType(type, holder.itemView.context)
    }

    override fun getItemCount(): Int {
        return types.size
    }

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener:OnItemClickListener){
        onItemClickListener = listener
    }
}

class PokemonListTypesViewHolder(
    view: View, listener: RVPokemonListTypesAdapter.OnItemClickListener
):RecyclerView.ViewHolder(view) {
    private val binding = PokemonListbytypeItemBinding.bind(view)

    init{
        view.setOnClickListener {
            listener.onItemClick(bindingAdapterPosition)
        }
    }

    fun bindType(type: String, context: Context?) {
        binding.pokemonType.text = type
        Utilities().setBackGroundColorOnType(binding.cardViewPokemonType,context, type)
    }

}
