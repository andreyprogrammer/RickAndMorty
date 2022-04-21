package com.example.andreymerkurev.rickandmorty.feature.locationdetails

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.andreymerkurev.rickandmorty.R
import com.example.anderymerkurev.data.network.PicassoLoader
import com.example.andreymerkurev.rickandmorty.databinding.FragmentLocationsDetailsBinding
import com.example.andreymerkurev.rickandmorty.di.Injector
import com.example.andreymerkurev.rickandmorty.di.ViewModelFactory
import com.example.andreymerkurev.rickandmorty.feature.episodedetails.EpisodesDetailsAdapter
import com.example.andreymerkurev.rickandmorty.feature.ItemClickListener
import javax.inject.Inject

class LocationsDetailsFragment : Fragment() {

    private lateinit var itemClickListener: ItemClickListener
    private lateinit var locationsDetailsAdapter: EpisodesDetailsAdapter
    private lateinit var binding: FragmentLocationsDetailsBinding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var picassoLoader: PicassoLoader

    val locationsDetailsViewModel: LocationsDetailsViewModel by viewModels {
        viewModelFactory
    }

    companion object {
        const val FRAGMENT_TAG = "FRAGMENT_LOCATIONS_DETAILS_TAG"
        private const val LOCATIONS_ID_EXTRA = "LOCATIONS_ID_EXTRA"
        fun newInstance(locationsId: Int) = LocationsDetailsFragment().also {
            it.arguments = Bundle().apply {
                putInt(LOCATIONS_ID_EXTRA, locationsId)
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
        Injector.getLocationsDetailsComponent().inject(this)
        val locationsId = "[${requireArguments().getInt(LOCATIONS_ID_EXTRA)}]"
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_locations_details, container, false
        )
        locationsDetailsViewModel.loadLocationData(locationsId)
        binding.locationsDetailsViewModel = locationsDetailsViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        locationsDetailsAdapter = EpisodesDetailsAdapter(
            onClick = { itemClickListener.onItemCharacterClick(it) },
            picassoLoader
        )
        binding.locationsDetailsRecyclerView.adapter = locationsDetailsAdapter

        locationsDetailsViewModel.isLoading.observeForever { isLoading ->
            if (isLoading) {
                binding.locationsDetailsProgress.visibility = View.VISIBLE
            }
            else {
                binding.locationsDetailsProgress.visibility = View.GONE
            }
        }

        locationsDetailsViewModel.isError.observeForever { isError ->
            if (isError) {
                binding.locationsDetailsErrorText.visibility = View.VISIBLE
            }
            else {
                binding.locationsDetailsErrorText.visibility = View.GONE
            }
        }

        return binding.root
    }

}