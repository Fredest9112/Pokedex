package com.learningkotlin.pokedex.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.learningkotlin.pokedex.R
import com.learningkotlin.pokedex.databinding.FragmentListPokemonTypeBinding
import com.learningkotlin.pokedex.interfaces.IPokemonPresenter
import com.learningkotlin.pokedex.interfaces.IPokemonView
import com.learningkotlin.pokedex.model.PokemonModel
import com.learningkotlin.pokedex.presenter.PokemonPresenter
import com.learningkotlin.pokedex.repository.database.Pokemon

class PokemonByTypeFragment : Fragment(), IPokemonView {

    private var binding: FragmentListPokemonTypeBinding? = null
    private val gridLayoutManager = GridLayoutManager(context, 1)
    private lateinit var typesArray:Array<String>
    private lateinit var pokemonTypesAdapter: RVPokemonListTypesAdapter
    private lateinit var pokemonByTypeAdapter: RVPokemonListAdapter
    private lateinit var iPokemonPresenter: IPokemonPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListPokemonTypeBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeRVWithPokemonTypes()

        iPokemonPresenter = PokemonPresenter(this,
            PokemonModel.getInstance(requireActivity().applicationContext)!!
        )
        pokemonTypesAdapter.setOnItemClickListener(object : RVPokemonListTypesAdapter.OnItemClickListener{
            override fun onItemClick(position: Int) {
                initializeRVWithPokemonByType()
                iPokemonPresenter.showPokemonInfoByType(typesArray[position])
            }

        })
    }

    private fun initializeRVWithPokemonByType() {
        pokemonByTypeAdapter = RVPokemonListAdapter()
        binding?.pokemonTypeRecycler?.layoutManager = gridLayoutManager
        binding?.pokemonTypeRecycler?.adapter = pokemonByTypeAdapter
    }

    private fun initializeRVWithPokemonTypes() {
        typesArray = resources.getStringArray(R.array.types)
        pokemonTypesAdapter = RVPokemonListTypesAdapter(typesArray.asList())
        binding?.pokemonTypeRecycler?.layoutManager = gridLayoutManager
        binding?.pokemonTypeRecycler?.adapter = pokemonTypesAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun showPokemon(pokemonInfo: List<Pokemon>) {
        pokemonByTypeAdapter.addPokemon(pokemonInfo)
    }

    override fun getLifeCycleOwner(): LifecycleOwner {
        return viewLifecycleOwner
    }

    override fun showEmptySearchMessage() {
        TODO("Not yet implemented")
    }
}