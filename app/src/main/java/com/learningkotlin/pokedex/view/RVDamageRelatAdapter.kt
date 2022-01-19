package com.learningkotlin.pokedex.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.learningkotlin.pokedex.R
import com.learningkotlin.pokedex.databinding.PokemonDamagerelatItemBinding
import com.learningkotlin.pokedex.databinding.PokemonDamagetypeItemBinding
import com.learningkotlin.pokedex.repository.constants.Constants.Companion.NO_TYPE
import com.learningkotlin.pokedex.repository.utilities.Utilities

class RVDamageRelatAdapter(val damageRelatList: List<List<String>>): RecyclerView.Adapter<DamageRelatViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DamageRelatViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return DamageRelatViewHolder(layoutInflater.inflate(R.layout.pokemon_damagerelat_item,parent,false))
    }

    override fun onBindViewHolder(holder: DamageRelatViewHolder, position: Int) {
        val data = damageRelatList[position]
        if(data.isNotEmpty()){
            holder.bindDamageRelat(data, holder.itemView.context)
        } else{
            holder.bindDamageRelat(listOf(NO_TYPE), holder.itemView.context)
        }
    }

    override fun getItemCount(): Int {
        return damageRelatList.size
    }
}

class DamageRelatViewHolder(view:View):RecyclerView.ViewHolder(view) {
    private val binding = PokemonDamagerelatItemBinding.bind(view)
    fun bindDamageRelat(data: List<String>, context: Context){
        val typeDamageAdapter = RVTypeDamageAdapter(data)
        val linearLayoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL, false)
        binding.rvDamageByType.layoutManager = linearLayoutManager
        binding.rvDamageByType.adapter = typeDamageAdapter
    }

    class RVTypeDamageAdapter(val data:List<String>):RecyclerView.Adapter<TypeAdapterViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TypeAdapterViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            return TypeAdapterViewHolder(layoutInflater.inflate(R.layout.pokemon_damagetype_item, parent, false))
        }

        override fun onBindViewHolder(holder: TypeAdapterViewHolder, position: Int) {
            val type = data[position]
            holder.bindType(type, holder.itemView.context)
        }

        override fun getItemCount(): Int {
            return data.size
        }
    }

    class TypeAdapterViewHolder(view: View):RecyclerView.ViewHolder(view) {
        private val binding = PokemonDamagetypeItemBinding.bind(view)
        fun bindType(type: String, context: Context) {
            binding.type.text = type.replaceFirstChar { it.uppercase() }
            Utilities().setBackGroundColorOnType(binding.cardViewType, context, type)
        }
    }
}
