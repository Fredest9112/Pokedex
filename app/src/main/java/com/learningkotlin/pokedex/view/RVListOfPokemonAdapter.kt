package com.learningkotlin.pokedex.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.learningkotlin.pokedex.R
import com.learningkotlin.pokedex.databinding.PokemonListItemBinding
import com.learningkotlin.pokedex.repository.constants.Constants.Companion.BUG
import com.learningkotlin.pokedex.repository.constants.Constants.Companion.DARK
import com.learningkotlin.pokedex.repository.constants.Constants.Companion.DRAGON
import com.learningkotlin.pokedex.repository.constants.Constants.Companion.ELECTRIC
import com.learningkotlin.pokedex.repository.constants.Constants.Companion.FAIRY
import com.learningkotlin.pokedex.repository.constants.Constants.Companion.FIGHTING
import com.learningkotlin.pokedex.repository.constants.Constants.Companion.FIRE
import com.learningkotlin.pokedex.repository.constants.Constants.Companion.FLYING
import com.learningkotlin.pokedex.repository.constants.Constants.Companion.GHOST
import com.learningkotlin.pokedex.repository.constants.Constants.Companion.GRASS
import com.learningkotlin.pokedex.repository.constants.Constants.Companion.GROUND
import com.learningkotlin.pokedex.repository.constants.Constants.Companion.ICE
import com.learningkotlin.pokedex.repository.constants.Constants.Companion.NORMAL
import com.learningkotlin.pokedex.repository.constants.Constants.Companion.POISON
import com.learningkotlin.pokedex.repository.constants.Constants.Companion.PSYCHIC
import com.learningkotlin.pokedex.repository.constants.Constants.Companion.ROCK
import com.learningkotlin.pokedex.repository.constants.Constants.Companion.STEEL
import com.learningkotlin.pokedex.repository.constants.Constants.Companion.WATER
import com.learningkotlin.pokedex.repository.database.Pokemon
import com.squareup.picasso.Picasso

class RVListOfPokemonAdapter : RecyclerView.Adapter<ListOfPokemonViewHolder>() {
    private val pokemonList = mutableListOf<Pokemon>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListOfPokemonViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ListOfPokemonViewHolder(
            layoutInflater.inflate(
                R.layout.pokemon_list_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ListOfPokemonViewHolder, position: Int) {
        val item = pokemonList[position]
        holder.bindInfo(item, holder.itemView.context)
        holder.bindPicture(item)
    }

    override fun getItemCount(): Int {
        return pokemonList.size
    }

    fun addPokemon(pokemonInfo: List<Pokemon>) {
        pokemonList.clear()
        pokemonList.addAll(pokemonInfo)
        notifyDataSetChanged()
    }
}

class ListOfPokemonViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = PokemonListItemBinding.bind(view)
    fun bindInfo(item: Pokemon, context: Context) {
        binding.cardBackGroundType2.visibility = View.INVISIBLE
        binding.pokemonId.text = item.id.toString()
        binding.pokemonName.text = item.name.replaceFirstChar { it.uppercase() }
        binding.pokemonDetailsType1.text = item.type1.replaceFirstChar { it.uppercase() }
        binding.pokemonDetailsType2.text = item.type2.replaceFirstChar { it.uppercase() }
        setBackgroundColorOnType(binding.cardBackgroundType1, context, item.type1)
        if (item.type2.isNotEmpty()) {
            binding.cardBackGroundType2.visibility = View.VISIBLE
            setBackgroundColorOnType(binding.cardBackGroundType2, context, item.type2)
        }
    }

    private fun setBackgroundColorOnType(cardView: CardView, context: Context, types: String) {
        when (types.lowercase()) {
            NORMAL -> cardView.setCardBackgroundColor(
                ContextCompat.getColor(
                    context,
                    R.color.normal
                )
            )
            FIRE -> cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.fire))
            WATER -> cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.water))
            ELECTRIC -> cardView.setCardBackgroundColor(
                ContextCompat.getColor(
                    context,
                    R.color.electric
                )
            )
            GRASS -> cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.grass))
            ICE -> cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.ice))
            FIGHTING -> cardView.setCardBackgroundColor(
                ContextCompat.getColor(
                    context,
                    R.color.fighting
                )
            )
            POISON -> cardView.setCardBackgroundColor(
                ContextCompat.getColor(
                    context,
                    R.color.poison
                )
            )
            GROUND -> cardView.setCardBackgroundColor(
                ContextCompat.getColor(
                    context,
                    R.color.ground
                )
            )
            FLYING -> cardView.setCardBackgroundColor(
                ContextCompat.getColor(
                    context,
                    R.color.flying
                )
            )
            PSYCHIC -> cardView.setCardBackgroundColor(
                ContextCompat.getColor(
                    context,
                    R.color.psychic
                )
            )
            BUG -> cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.bug))
            ROCK -> cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.rock))
            GHOST -> cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.ghost))
            DRAGON -> cardView.setCardBackgroundColor(
                ContextCompat.getColor(
                    context,
                    R.color.dragon
                )
            )
            DARK -> cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.dark))
            STEEL -> cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.steel))
            FAIRY -> cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.fairy))
        }
    }

    fun bindPicture(picture: Pokemon) {
        Picasso.get().load(picture.image).fit()
            .into(binding.pokemonImage)
    }
}