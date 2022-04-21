package com.example.andreymerkurev.rickandmorty.feature.characterfilter

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.anderymerkurev.domain.entities.Gender
import com.example.anderymerkurev.domain.entities.Status
import com.example.andreymerkurev.rickandmorty.R
import com.example.andreymerkurev.rickandmorty.databinding.FragmentCharactersFilterBinding
import com.example.andreymerkurev.rickandmorty.di.Injector
import com.example.andreymerkurev.rickandmorty.di.ViewModelFactory
import com.example.andreymerkurev.rickandmorty.feature.ItemClickListener
import javax.inject.Inject


class CharactersFilterFragment : Fragment() {

    private var _binding: FragmentCharactersFilterBinding? = null
    private val binding get() = _binding!!

    private lateinit var itemClickListener: ItemClickListener

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    val charactersFilterViewModel: CharactersFilterViewModel by viewModels {
        viewModelFactory
    }

    companion object {
        const val FRAGMENT_TAG = "FRAGMENT_CHARACTER_FILTER_TAG"
        fun newInstance() = CharactersFilterFragment()
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
        _binding = FragmentCharactersFilterBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Injector.getCharactersFilterComponent().inject(this)
        initViews()

        binding.characterFilterName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                charactersFilterViewModel.setName(s.toString())
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })

        binding.characterFilterSpecies.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                charactersFilterViewModel.setSpecies(s.toString())
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })

        binding.characterFilterType.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                charactersFilterViewModel.setType(s.toString())
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })

        binding.characterFilterRadioStatus.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.characterFilterStatusNone -> {
                    charactersFilterViewModel.setStatus(Status.NONE)
                }
                R.id.characterFilterStatusAlive -> {
                    charactersFilterViewModel.setStatus(Status.ALIVE)
                }
                R.id.characterFilterStatusDead -> {
                    charactersFilterViewModel.setStatus(Status.DEAD)
                }
                R.id.characterFilterStatusUnknown -> {
                    charactersFilterViewModel.setStatus(Status.UNKNOWN)
                }
            }
        }

        binding.characterFilterRadioGender.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.characterFilterGenderNone -> {
                    charactersFilterViewModel.setGender(Gender.NONE)
                }
                R.id.characterFilterGenderFemale -> {
                    charactersFilterViewModel.setGender(Gender.FEMALE)
                }
                R.id.characterFilterGenderMale -> {
                    charactersFilterViewModel.setGender(Gender.MALE)
                }
                R.id.characterFilterGenderGenderless -> {
                    charactersFilterViewModel.setGender(Gender.GENDERLESS)
                }
                R.id.characterFilterGenderUnknown -> {
                    charactersFilterViewModel.setGender(Gender.UNKNOWN)
                }
            }
        }

        binding.characterFilterBtnShow.setOnClickListener {
            itemClickListener.showFilteringCharactersClick(getData())
        }
    }

    private fun initViews() {
        binding.characterFilterName.text = SpannableStringBuilder(charactersFilterViewModel.name.value)
        binding.characterFilterSpecies.text = SpannableStringBuilder(charactersFilterViewModel.species.value)
        binding.characterFilterType.text = SpannableStringBuilder(charactersFilterViewModel.type.value)

        when (charactersFilterViewModel.status.value) {
            Status.ALIVE -> {
                binding.characterFilterStatusAlive.isChecked = true
            }
            Status.DEAD -> {
                binding.characterFilterStatusDead.isChecked = true
            }
            Status.UNKNOWN -> {
                binding.characterFilterStatusUnknown.isChecked = true
            }
            else -> {
                binding.characterFilterStatusNone.isChecked = true
            }
        }

        when (charactersFilterViewModel.gender.value) {
            Gender.FEMALE -> {
                binding.characterFilterGenderFemale.isChecked = true
            }
            Gender.MALE -> {
                binding.characterFilterGenderMale.isChecked = true
            }
            Gender.UNKNOWN -> {
                binding.characterFilterGenderUnknown.isChecked = true
            }
            Gender.GENDERLESS -> {
                binding.characterFilterGenderGenderless.isChecked = true
            }
            else -> {
                binding.characterFilterGenderNone.isChecked = true
            }
        }
    }

    private fun getData(): ArrayList<String> {
        val name = charactersFilterViewModel.name.value!!
        val species = charactersFilterViewModel.species.value!!
        val type = charactersFilterViewModel.type.value!!
        val status = when (charactersFilterViewModel.status.value) {
            Status.ALIVE -> {
                "alive"
            }
            Status.DEAD -> {
                "dead"
            }
            Status.UNKNOWN -> {
                "unknown"
            }
            else -> {
                ""
            }
        }

        val gender = when (charactersFilterViewModel.gender.value) {
            Gender.FEMALE -> {
                "female"
            }
            Gender.MALE -> {
                "male"
            }
            Gender.UNKNOWN -> {
                "unknown"
            }
            Gender.GENDERLESS -> {
                "genderless"
            }
            else -> {
                ""
            }
        }
        Log.d("key01", arrayListOf(name, status, species, type, gender).toString())
        return arrayListOf(name, status, species, type, gender)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}