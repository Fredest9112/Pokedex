package com.learningkotlin.pokedex.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.GridLayoutManager
import com.learningkotlin.pokedex.databinding.FragmentListPokemonBinding
import com.learningkotlin.pokedex.interfaces.IPokemonPresenter
import com.learningkotlin.pokedex.interfaces.IPokemonView
import com.learningkotlin.pokedex.model.PokemonModel
import com.learningkotlin.pokedex.presenter.PokemonPresenter
import com.learningkotlin.pokedex.repository.database.Pokemon

class PokemonListFragment : Fragment(), IPokemonView {

    private var binding: FragmentListPokemonBinding? = null
    private lateinit var adapter: RVListOfPokemonAdapter
    private lateinit var iPokemonPresenter: IPokemonPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListPokemonBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeRV()
        binding = FragmentListPokemonBinding.bind(view)
        iPokemonPresenter = PokemonPresenter(this, PokemonModel(requireContext()))
        iPokemonPresenter.pokemonInfo()
        iPokemonPresenter.showPokemonInfo()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onStop() {
        iPokemonPresenter.cancelCurrentJob()
        super.onStop()
    }

    private fun initializeRV() {
        adapter = RVListOfPokemonAdapter()
        val gridLayoutManager = GridLayoutManager(context, 1)
        binding?.pokemonIdRecycler?.layoutManager = gridLayoutManager
        binding?.pokemonIdRecycler?.adapter = adapter
    }

    override fun showPokemon(pokemonInfo: List<Pokemon>) {
        adapter.addPokemon(pokemonInfo)
    }

    override fun getLifeCycleOwner(): LifecycleOwner {
        return viewLifecycleOwner
    }

    override fun showMessageEmptySearch(message: String) {
    }
}