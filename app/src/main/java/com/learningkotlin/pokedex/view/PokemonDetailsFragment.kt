package com.learningkotlin.pokedex.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.learningkotlin.pokedex.R
import com.learningkotlin.pokedex.databinding.FragmentPokemonDetailsBinding
import com.learningkotlin.pokedex.interfaces.IPokemonPresenter
import com.learningkotlin.pokedex.interfaces.IPokemonViewDetails
import com.learningkotlin.pokedex.model.PokemonModel
import com.learningkotlin.pokedex.presenter.PokemonPresenter
import com.learningkotlin.pokedex.repository.api.pokemonSpeciesEndPoint.PokemonSpecies
import com.learningkotlin.pokedex.repository.database.Pokemon
import com.learningkotlin.pokedex.repository.utilities.Utilities
import kotlin.properties.Delegates

class PokemonDetailsFragment : Fragment(), IPokemonViewDetails {

    private var binding: FragmentPokemonDetailsBinding? = null
    private lateinit var iPokemonPresenter: IPokemonPresenter
    private lateinit var pokemonDetails:Pokemon
    private lateinit var abilityAdapter: RVAbilitiesPokemonAdapter
    private lateinit var evolutionsAdapter: RVEvolutionsPokemonAdapter
    private lateinit var damageRelatAdapterT1: RVDamageRelatAdapter
    private lateinit var damageRelatAdapterT2: RVDamageRelatAdapter
    private var index by Delegates.notNull<Int>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPokemonDetailsBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        index = requireArguments().getInt("pokemonId")
        iPokemonPresenter = PokemonPresenter(this,
            PokemonModel.getInstance(requireActivity().applicationContext)!!)
        iPokemonPresenter.showPokemonDetails(index.toString())

        binding?.cardBackgroundType2?.visibility = View.GONE
        binding?.cardViewT2?.visibility = View.GONE

    }

    override fun showPokemonBasicInfo(pokemon: Pokemon) {
        pokemonDetails = pokemon
        (activity as AppCompatActivity).supportActionBar?.title = pokemonDetails.id
            .toString()+" "+pokemonDetails.name.replaceFirstChar { it.uppercase() }
        binding?.pokemonDetailsType1?.text = pokemonDetails.type1.replaceFirstChar { it.uppercase() }
        Utilities().setBackGroundColorOnType(
            binding?.cardBackgroundType1, context, pokemonDetails.type1)
        if(pokemonDetails.type2.isNotEmpty()){
            binding?.cardBackgroundType2?.visibility = View.VISIBLE
            binding?.cardViewT2?.visibility = View.VISIBLE
            Utilities().setBackGroundColorOnType(
                binding?.cardBackgroundType2, context, pokemonDetails.type2)
            binding?.pokemonDetailsType2?.text = pokemonDetails.type2.replaceFirstChar { it.uppercase() }
        }
        Utilities().bindPicture(pokemonDetails.image, binding?.imagePokemonDetails)
        binding?.pokemonWeightDetails?.text = resources.getString(R.string.kilograms, pokemonDetails.weight.toString())
        binding?.pokemonHeightDetails?.text = resources.getString(R.string.meters, pokemonDetails.height.toString())
        Utilities().setStatsLayout(binding?.hpValue, binding?.progressBarHP, pokemonDetails.hp)
        Utilities().setStatsLayout(binding?.attackValue, binding?.progressBarAttack, pokemonDetails.attack)
        Utilities().setStatsLayout(binding?.defenseValue, binding?.progressBarDefense, pokemonDetails.defense)
        Utilities().setStatsLayout(binding?.spAttackValue, binding?.progressBarspAttack, pokemonDetails.spAttack)
        Utilities().setStatsLayout(binding?.spDefenseValue, binding?.progressBarspDefense, pokemonDetails.spDefense)
        Utilities().setStatsLayout(binding?.speedValue, binding?.progressBarSpeed, pokemonDetails.speed)
    }

    override fun showAdditionalInfo(
        pokemonSpecies: PokemonSpecies,
        pokemonEvolutions: List<Pokemon>,
        damageRelationsType1: List<List<String>>,
        damageRelationsType2: List<List<String>>,
        abilityInfo: Map<String, String>
    ) {
        for(language in pokemonSpecies.genera){
            if(language.language.name == "en"){
                binding?.pokemonSpecieDetails?.text = language.genus
            }
        }
        for(language in pokemonSpecies.flavorTextEntries){
            if(language.language.name == "en"){
                binding?.pokemonDescriptionDetails?.text = language.flavorText
                    .replace("\\s+".toRegex(), " ").lowercase()
                    .replaceFirstChar { it.uppercase() }
            }
        }
        Log.i("damageType1","$damageRelationsType1")
        Log.i("damageType2","$damageRelationsType2")
        initRVSkills(abilityInfo)
        initRVEvolutions(pokemonEvolutions)
        initRVDamageRelatT1(damageRelationsType1)
        initRVDamageRelatT2(damageRelationsType2)
    }

    private fun initRVDamageRelatT1(damageRelationsType1: List<List<String>>) {
        damageRelatAdapterT1 = RVDamageRelatAdapter(damageRelationsType1)
        val linearLayoutManager = LinearLayoutManager(context)
        binding?.rvDamageRelatT1?.layoutManager = linearLayoutManager
        binding?.rvDamageRelatT1?.adapter = damageRelatAdapterT1
    }

    private fun initRVDamageRelatT2(damageRelationsType2: List<List<String>>) {
        damageRelatAdapterT1 = RVDamageRelatAdapter(damageRelationsType2)
        val linearLayoutManager = LinearLayoutManager(context)
        binding?.rvDamageRelatT2?.layoutManager = linearLayoutManager
        binding?.rvDamageRelatT2?.adapter = damageRelatAdapterT1
    }

    private fun initRVEvolutions(pokemonEvolutions: List<Pokemon>) {
        evolutionsAdapter = RVEvolutionsPokemonAdapter(pokemonEvolutions)
        val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding?.rvEvolutions?.layoutManager = linearLayoutManager
        binding?.rvEvolutions?.adapter = evolutionsAdapter
    }

    private fun initRVSkills(abilityInfo: Map<String, String>) {
        abilityAdapter = RVAbilitiesPokemonAdapter(abilityInfo)
        val linearLayoutManager = LinearLayoutManager(context)
        binding?.rvSkills?.layoutManager = linearLayoutManager
        binding?.rvSkills?.adapter = abilityAdapter
    }

    override fun getLifeCycleOwner(): LifecycleOwner {
        return viewLifecycleOwner
    }

    override fun getContext(): Context? {
        return requireActivity().applicationContext
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as AppCompatActivity).supportActionBar?.title = context?.resources?.getString(R.string.app_name)
        binding = null
    }
}