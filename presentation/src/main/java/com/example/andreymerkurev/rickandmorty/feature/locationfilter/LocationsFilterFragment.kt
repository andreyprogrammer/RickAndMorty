package com.example.andreymerkurev.rickandmorty.feature.locationfilter

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.andreymerkurev.rickandmorty.databinding.FragmentLocationsFilterBinding
import com.example.andreymerkurev.rickandmorty.di.Injector
import com.example.andreymerkurev.rickandmorty.di.ViewModelFactory
import com.example.andreymerkurev.rickandmorty.feature.ItemClickListener
import javax.inject.Inject

class LocationsFilterFragment : Fragment() {

    private var _binding: FragmentLocationsFilterBinding? = null
    private val binding get() = _binding!!

    private lateinit var itemClickListener: ItemClickListener

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    val locationsFilterViewModel: LocationsFilterViewModel by viewModels {
        viewModelFactory
    }

    companion object {
        const val FRAGMENT_TAG = "FRAGMENT_LOCATIONS_FILTER_TAG"
        fun newInstance() = LocationsFilterFragment()
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
        _binding = FragmentLocationsFilterBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Injector.getLocationsFilterComponent().inject(this)
        initViews()

        binding.locationsFilterName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                locationsFilterViewModel.setName(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })

        binding.locationsFilterType.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                locationsFilterViewModel.setType(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })

        binding.locationsFilterDimension.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                locationsFilterViewModel.setDimension(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })

        binding.locationsFilterBtnShow.setOnClickListener {
            itemClickListener.showFilteringLocationsClick(getData())
        }
        
    }

    private fun initViews() {
        binding.locationsFilterName.text =
            SpannableStringBuilder(locationsFilterViewModel.name.value)
        binding.locationsFilterType.text =
            SpannableStringBuilder(locationsFilterViewModel.type.value)
        binding.locationsFilterDimension.text =
            SpannableStringBuilder(locationsFilterViewModel.dimension.value)
    }

    private fun getData(): ArrayList<String> {
        val name = locationsFilterViewModel.name.value!!
        val type = locationsFilterViewModel.type.value!!
        val dimension = locationsFilterViewModel.dimension.value!!
        return arrayListOf(name, type, dimension)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}