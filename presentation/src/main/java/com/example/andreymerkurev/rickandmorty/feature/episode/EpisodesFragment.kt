package com.example.andreymerkurev.rickandmorty.feature.episode

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.andreymerkurev.rickandmorty.databinding.FragmentEpisodesBinding
import com.example.andreymerkurev.rickandmorty.di.Injector
import com.example.andreymerkurev.rickandmorty.di.ViewModelFactory
import com.example.andreymerkurev.rickandmorty.feature.ItemClickListener
import kotlinx.coroutines.launch
import javax.inject.Inject

class EpisodesFragment : Fragment() {

    private var _binding: FragmentEpisodesBinding? = null
    private val binding get() = _binding!!

    private lateinit var episodesAdapter: EpisodesAdapter
    private lateinit var itemClickListener: ItemClickListener

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    val episodesViewModel: EpisodesViewModel by viewModels {
        viewModelFactory
    }

    companion object {
        const val FRAGMENT_TAG = "FRAGMENT_EPISODES_TAG"
        private const val EPISODE_ID_EXTRA = "EPISODE_ID_EXTRA"
        fun newInstance(str: ArrayList<String>) = EpisodesFragment().also {
            it.arguments = Bundle().apply {
                putStringArrayList(EPISODE_ID_EXTRA, str)
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        itemClickListener = context as ItemClickListener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEpisodesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Injector.getEpisodesComponent().inject(this)

        episodesAdapter = EpisodesAdapter(
            onClick = { itemClickListener.onItemEpisodeClick(it) }
        )
        binding.episodesRecyclerView.adapter = episodesAdapter
        val listParams = arrayListOf("", "")
        val args = requireArguments().getStringArrayList(EPISODE_ID_EXTRA)
        if (!args.isNullOrEmpty()) {
            listParams.clear()
            listParams.addAll(requireArguments().getStringArrayList(EPISODE_ID_EXTRA)!!)
        }
        episodesViewModel.setRequestParams(listParams)
        lifecycleScope.launch {
            try {
                episodesViewModel.listEpisodes.collect {
                     episodesAdapter.submitData(it)
                }
            } catch (e: Throwable) {
                print("Exception from the flow: $e")
            }
        }

        binding.episodesBtnFind.setOnClickListener {
            val searchString = binding.episodesEditFind.text.toString()
            if (searchString.isNotEmpty()) {
                itemClickListener.showFilteringEpisodesClick(
                    arrayListOf(searchString, "1")
                )
            }
        }

        binding.episodesSwipeRefresh.setOnRefreshListener {
            itemClickListener.episodesRefresh()
            binding.episodesSwipeRefresh.isRefreshing = false
        }

        binding.episodesBtnFilter.setOnClickListener {
            itemClickListener.onEpisodeFilterClick()
        }

        episodesViewModel.isLoading.observeForever { isLoading ->
            if (isLoading) {
                binding.episodesProgress.visibility = View.VISIBLE
            }
            else {
                binding.episodesProgress.visibility = View.GONE
            }
        }

        episodesViewModel.isError.observeForever { isError ->
            if (isError) {
                binding.episodesErrorText.visibility = View.VISIBLE
            }
            else {
                binding.episodesErrorText.visibility = View.GONE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}