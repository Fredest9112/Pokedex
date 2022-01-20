package com.learningkotlin.pokedex.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.learningkotlin.pokedex.databinding.FragmentListPokemonBinding
import com.learningkotlin.pokedex.model.PokemonSingletonViewModel
import com.learningkotlin.pokedex.model.PokemonViewModel

class PokemonListFragment : Fragment() {

    private var binding:FragmentListPokemonBinding? = null
    private lateinit var pokemonViewModel:PokemonViewModel
    private lateinit var pokemonListAdapter:RVPokemonListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListPokemonBinding.inflate(inflater, container,false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pokemonViewModel = PokemonSingletonViewModel.getInstance(
            requireContext().applicationContext, requireActivity()
        )!!
        initializeRV()
        pokemonViewModel.loadPokemon().observe(viewLifecycleOwner, {
            pokemonListAdapter.addPokemon(it)
        })
    }

    private fun initializeRV() {
        pokemonListAdapter = RVPokemonListAdapter()
        pokemonListAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        val grindLayoutManager = GridLayoutManager(context, 1)
        binding?.pokemonIdRecycler?.layoutManager = grindLayoutManager
        binding?.pokemonIdRecycler?.adapter = pokemonListAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}