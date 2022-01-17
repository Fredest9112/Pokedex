package com.learningkotlin.pokedex.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.learningkotlin.pokedex.databinding.FragmentListPokemonBinding
import com.learningkotlin.pokedex.interfaces.IPokemonPresenter
import com.learningkotlin.pokedex.interfaces.IPokemonView
import com.learningkotlin.pokedex.model.PokemonModel
import com.learningkotlin.pokedex.presenter.PokemonPresenter
import com.learningkotlin.pokedex.repository.database.Pokemon

class PokemonListFragment : Fragment(), IPokemonView {

    private var binding:FragmentListPokemonBinding? = null
    private lateinit var adapter : RVPokemonListAdapter
    private lateinit var iPokemonPresenter: IPokemonPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListPokemonBinding.inflate(inflater, container,false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeRV()

        iPokemonPresenter = PokemonPresenter(this,
            PokemonModel.getInstance(requireActivity().applicationContext)!!)
        iPokemonPresenter.showPokemonInfo()
    }

    private fun initializeRV() {
        adapter = RVPokemonListAdapter()
        adapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        val gridLayoutManager = GridLayoutManager(context,1)
        binding?.pokemonIdRecycler?.layoutManager = gridLayoutManager
        binding?.pokemonIdRecycler?.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun showPokemon(pokemonInfo: List<Pokemon>) {
        adapter.addPokemon(pokemonInfo)
    }

    override fun getLifeCycleOwner(): LifecycleOwner {
        return viewLifecycleOwner
    }

    override fun showEmptySearchMessage() {
        TODO("Not yet implemented")
    }
}