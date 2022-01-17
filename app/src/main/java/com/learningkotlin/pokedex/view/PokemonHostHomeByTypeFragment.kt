package com.learningkotlin.pokedex.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.learningkotlin.pokedex.R
import com.learningkotlin.pokedex.databinding.FragmentHostHomeByTypePokemonBinding

class PokemonHostHomeByTypeFragment : Fragment() {

    private var binding: FragmentHostHomeByTypePokemonBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHostHomeByTypePokemonBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.bottomNavigationView?.setOnItemSelectedListener {
            lateinit var fragment: Fragment
            when (it.itemId) {
                R.id.listPokemonId -> fragment = PokemonListFragment()
                R.id.listPokemonType -> fragment = PokemonByTypeFragment()
            }

            childFragmentManager.beginTransaction().replace(
                R.id.hostHomeByTypeFragmentContainer, fragment
            ).commit()
            return@setOnItemSelectedListener true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}