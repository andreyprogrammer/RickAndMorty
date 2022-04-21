package com.example.andreymerkurev.rickandmorty.feature.character

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import com.example.anderymerkurev.data.network.PicassoLoader
import com.example.andreymerkurev.rickandmorty.databinding.FragmentCharactersBinding
import com.example.andreymerkurev.rickandmorty.di.Injector
import com.example.andreymerkurev.rickandmorty.di.ViewModelFactory
import com.example.andreymerkurev.rickandmorty.feature.ItemClickListener
import kotlinx.coroutines.launch
import javax.inject.Inject


class CharactersFragment : Fragment() {
    private var _binding: FragmentCharactersBinding? = null
    private val binding get() = _binding!!

    private lateinit var charactersAdapter: CharactersAdapter
    private lateinit var itemClickListener: ItemClickListener

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var picassoLoader: PicassoLoader

    val charactersViewModel: CharactersViewModel by viewModels {
        viewModelFactory
    }

    companion object {
        const val FRAGMENT_TAG = "FRAGMENT_CHARACTERS_TAG"
        private const val CHARACTER_ID_EXTRA = "CHARACTER_ID_EXTRA"
        fun newInstance(str: ArrayList<String>) = CharactersFragment().also {
            it.arguments = Bundle().apply {
                putStringArrayList(CHARACTER_ID_EXTRA, str)
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
        _binding = FragmentCharactersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Injector.getCharacterComponent().inject(this)

        charactersAdapter = CharactersAdapter(
            onClick = { itemClickListener.onItemCharacterClick(it) },
            picassoLoader
        )
        binding.charactersRecyclerView.adapter = charactersAdapter
        val listParams = arrayListOf("", "", "", "", "")
        val args = requireArguments().getStringArrayList(CHARACTER_ID_EXTRA)
        if (!args.isNullOrEmpty()) {
            listParams.clear()
            listParams.addAll(requireArguments().getStringArrayList(CHARACTER_ID_EXTRA)!!)
        }

        charactersViewModel.setRequestParams(listParams)

        lifecycleScope.launch {
            try {
                charactersViewModel.listCharacters.collect {
                    charactersAdapter.submitData(it)
                }
            } catch (e: Throwable) {
                print("Exception from the flow: $e")
            }
        }

        binding.charactersBtnFind.setOnClickListener {
            val searchString = binding.charactersEditFind.text.toString()
            if (searchString.isNotEmpty()) {
                itemClickListener.showFilteringCharactersClick(
                    arrayListOf(searchString, "", "", "", "1")
                )
            }
        }

        binding.charactersSwipeRefresh.setOnRefreshListener {
            itemClickListener.charactersRefresh()
            binding.charactersSwipeRefresh.isRefreshing = false
        }

        binding.charactersBtnFilter.setOnClickListener {
            itemClickListener.onCharacterFilterClick()
        }

        charactersViewModel.isLoading.observeForever { isLoading ->
            if (isLoading) {
                binding.charactersProgress.visibility = View.VISIBLE
            } else {
                binding.charactersProgress.visibility = View.GONE
            }
        }

        charactersViewModel.isError.observeForever { isError ->
            if (isError) {
                binding.charactersErrorText.visibility = View.VISIBLE
            } else {
                binding.charactersErrorText.visibility = View.GONE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}