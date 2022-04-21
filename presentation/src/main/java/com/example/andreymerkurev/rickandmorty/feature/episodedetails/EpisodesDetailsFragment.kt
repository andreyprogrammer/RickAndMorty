package com.example.andreymerkurev.rickandmorty.feature.episodedetails

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.anderymerkurev.data.network.PicassoLoader
import com.example.andreymerkurev.rickandmorty.R
import com.example.andreymerkurev.rickandmorty.databinding.FragmentEpisodesDetailsBinding
import com.example.andreymerkurev.rickandmorty.di.Injector
import com.example.andreymerkurev.rickandmorty.di.ViewModelFactory
import com.example.andreymerkurev.rickandmorty.feature.ItemClickListener
import javax.inject.Inject

class EpisodesDetailsFragment : Fragment() {

    private lateinit var itemClickListener: ItemClickListener
    private lateinit var episodesDetailsAdapter: EpisodesDetailsAdapter
    private lateinit var binding: FragmentEpisodesDetailsBinding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var picassoLoader: PicassoLoader

    val episodesDetailsViewModel: EpisodesDetailsViewModel by viewModels {
        viewModelFactory
    }

    companion object {
        const val FRAGMENT_TAG = "FRAGMENT_EPISODES_DETAILS_TAG"
        private const val EPISODES_ID_EXTRA = "EPISODES_ID_EXTRA"
        fun newInstance(episodesId: Int) = EpisodesDetailsFragment().also {
            it.arguments = Bundle().apply {
                putInt(EPISODES_ID_EXTRA, episodesId)
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
        Injector.getEpisodesDetailsComponent().inject(this)
        val episodeId = "[${requireArguments().getInt(EPISODES_ID_EXTRA)}]"
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_episodes_details, container, false
        )
        episodesDetailsViewModel.loadEpisodeData(episodeId)
        binding.episodesDetailsViewModel = episodesDetailsViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        episodesDetailsAdapter = EpisodesDetailsAdapter(
            onClick = { itemClickListener.onItemCharacterClick(it) },
            picassoLoader
        )
        binding.episodesDetailsRecyclerView.adapter = episodesDetailsAdapter

        episodesDetailsViewModel.isLoading.observeForever { isLoading ->
            if (isLoading) {
                binding.episodesDetailsProgress.visibility = View.VISIBLE
            }
            else {
                binding.episodesDetailsProgress.visibility = View.GONE
            }
        }

        episodesDetailsViewModel.isError.observeForever { isError ->
            if (isError) {
                binding.episodesDetailsErrorText.visibility = View.VISIBLE
            }
            else {
                binding.episodesDetailsErrorText.visibility = View.GONE
            }
        }

        return binding.root
    }
}