package com.example.andreymerkurev.rickandmorty.feature.episodefilter

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
import com.example.andreymerkurev.rickandmorty.databinding.FragmentCharactersFilterBinding
import com.example.andreymerkurev.rickandmorty.databinding.FragmentEpisodesFilterBinding
import com.example.andreymerkurev.rickandmorty.di.Injector
import com.example.andreymerkurev.rickandmorty.di.ViewModelFactory
import com.example.andreymerkurev.rickandmorty.feature.ItemClickListener
import javax.inject.Inject

class EpisodesFilterFragment : Fragment() {

    private var _binding: FragmentEpisodesFilterBinding? = null
    private val binding get() = _binding!!

    private lateinit var itemClickListener: ItemClickListener

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    val episodesFilterViewModel: EpisodesFilterViewModel by viewModels {
        viewModelFactory
    }

    companion object {
        const val FRAGMENT_TAG = "FRAGMENT_EPISODES_FILTER_TAG"
        fun newInstance() = EpisodesFilterFragment()
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
        _binding = FragmentEpisodesFilterBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Injector.getEpisodesFilterComponent().inject(this)
        initViews()

        binding.episodesFilterName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                episodesFilterViewModel.setName(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })

        binding.episodesFilterEpisode.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                episodesFilterViewModel.setEpisode(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })

        binding.episodesFilterBtnShow.setOnClickListener {
            itemClickListener.showFilteringEpisodesClick(getData())
        }
    }

    private fun initViews() {
        binding.episodesFilterName.text =
            SpannableStringBuilder(episodesFilterViewModel.name.value)
        binding.episodesFilterEpisode.text =
            SpannableStringBuilder(episodesFilterViewModel.episode.value)
    }

    private fun getData(): ArrayList<String> {
        val name = episodesFilterViewModel.name.value!!
        val episode = episodesFilterViewModel.episode.value!!
        return arrayListOf(name,episode)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}