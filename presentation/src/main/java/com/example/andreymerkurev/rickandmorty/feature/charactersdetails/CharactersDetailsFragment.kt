package com.example.andreymerkurev.rickandmorty.feature.charactersdetails

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.anderymerkurev.data.network.PicassoLoader
import com.example.andreymerkurev.rickandmorty.R
import com.example.andreymerkurev.rickandmorty.databinding.FragmentCharactersDetailsBinding
import com.example.andreymerkurev.rickandmorty.di.Injector
import com.example.andreymerkurev.rickandmorty.di.ViewModelFactory
import com.example.andreymerkurev.rickandmorty.feature.ItemClickListener
import com.example.andreymerkurev.rickandmorty.feature.takeLastInt
import javax.inject.Inject

class CharactersDetailsFragment : Fragment() {

    private lateinit var itemClickListener: ItemClickListener
    private lateinit var charactersDetailsAdapter: CharactersDetailsAdapter
    private lateinit var binding: FragmentCharactersDetailsBinding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var picassoLoader: PicassoLoader

    val charactersDetailsViewModel: CharactersDetailsViewModel by viewModels {
        viewModelFactory
    }

    companion object {
        const val FRAGMENT_TAG = "FRAGMENT_CHARACTERS_DETAILS_TAG"
        private const val CHARACTER_ID_EXTRA = "CHARACTER_DETAILS_ID_EXTRA"
        fun newInstance(characterId: Int) = CharactersDetailsFragment().also {
            it.arguments = Bundle().apply {
                putInt(CHARACTER_ID_EXTRA, characterId)
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        itemClickListener = context as ItemClickListener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Injector.getCharactersDetailsComponent().inject(this)
        val characterId = "[${requireArguments().getInt(CHARACTER_ID_EXTRA)}]"
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_characters_details, container, false
        )
        charactersDetailsViewModel.loadCharacterData(characterId)
        binding.charactersDetailsViewModel = charactersDetailsViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        charactersDetailsAdapter = CharactersDetailsAdapter(
            onClick = { itemClickListener.onItemEpisodeClick(it) }
        )
        binding.charactersDetailsRecyclerView.adapter = charactersDetailsAdapter

        binding.charactersDetailsOrigin.setOnClickListener {
            val loc = charactersDetailsViewModel.character.value!!.origin.url
            if (loc.isNotEmpty()) {
                itemClickListener.onItemLocationClick(
                    loc.takeLastInt()
                )
            }
        }

        binding.charactersDetailsLocation.setOnClickListener {
            val loc = charactersDetailsViewModel.character.value!!.location.url
            if (loc.isNotEmpty()) {
                itemClickListener.onItemLocationClick(
                    loc.takeLastInt()
                )
            }
        }

        charactersDetailsViewModel.isLoading.observeForever { isLoading ->
            if (isLoading) {
                binding.charactersDetailsProgress.visibility = View.VISIBLE
            }
            else {
                binding.charactersDetailsProgress.visibility = View.GONE
            }
        }

        charactersDetailsViewModel.isError.observeForever { isError ->
            if (isError) {
                binding.charactersDetailsErrorText.visibility = View.VISIBLE
            }
            else {
                binding.charactersDetailsErrorText.visibility = View.GONE
            }
        }

        return binding.root
    }


}