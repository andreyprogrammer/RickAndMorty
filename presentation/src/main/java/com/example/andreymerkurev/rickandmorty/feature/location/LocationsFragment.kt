package com.example.andreymerkurev.rickandmorty.feature.location

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import com.example.andreymerkurev.rickandmorty.databinding.FragmentLocationsBinding
import com.example.andreymerkurev.rickandmorty.di.Injector
import com.example.andreymerkurev.rickandmorty.di.ViewModelFactory
import com.example.andreymerkurev.rickandmorty.feature.ItemClickListener
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class LocationsFragment : Fragment() {

    private var _binding: FragmentLocationsBinding? = null
    private val binding get() = _binding!!

    private lateinit var locationsAdapter: LocationsAdapter
    private lateinit var itemClickListener: ItemClickListener

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    val locationsViewModel: LocationsViewModel by viewModels {
        viewModelFactory
    }

    companion object {
        const val FRAGMENT_TAG = "FRAGMENT_LOCATIONS_TAG"
        private const val LOCATION_ID_EXTRA = "LOCATION_ID_EXTRA"
        fun newInstance(str: ArrayList<String>) = LocationsFragment().also {
            it.arguments = Bundle().apply {
                putStringArrayList(LOCATION_ID_EXTRA, str)
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
    ): View? {
        _binding = FragmentLocationsBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Injector.getLocationsComponent().inject(this)
        locationsAdapter = LocationsAdapter(
            onClick = { itemClickListener.onItemLocationClick(it) }
        )
        binding.locationsRecyclerView.adapter = locationsAdapter
        val args = requireArguments().getStringArrayList(LOCATION_ID_EXTRA)
        val listParams = arrayListOf("", "", "")
        if (!args.isNullOrEmpty()) {
            listParams.clear()
            listParams.addAll(requireArguments().getStringArrayList(LOCATION_ID_EXTRA)!!)
        }
        locationsViewModel.setRequestParams(listParams)
        lifecycleScope.launch {
            try {
                locationsViewModel.listLocations.collect {
                    locationsAdapter.submitData(it)
                }
            } catch (e: Throwable) {
                print("Exception from the flow: $e")
            }
        }

        binding.locationsBtnFind.setOnClickListener {
            val searchString = binding.locationsEditFind.text.toString()
            if (searchString.isNotEmpty()) {
                itemClickListener.showFilteringLocationsClick(
                    arrayListOf(searchString, "", "1")
                )
            }
        }

        binding.locationsSwipeRefresh.setOnRefreshListener {
            itemClickListener.locationsRefresh()
            binding.locationsSwipeRefresh.isRefreshing = false
        }

        binding.locationsBtnFilter.setOnClickListener {
            itemClickListener.onLocationFilterClick()
        }

        locationsViewModel.isLoading.observeForever { isLoading ->
            if (isLoading) {
                binding.locationsProgress.visibility = View.VISIBLE
            }
            else {
                binding.locationsProgress.visibility = View.GONE
            }
        }

        locationsViewModel.isError.observeForever { isError ->
            if (isError) {
                binding.locationsErrorText.visibility = View.VISIBLE
            }
            else {
                binding.locationsErrorText.visibility = View.GONE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}