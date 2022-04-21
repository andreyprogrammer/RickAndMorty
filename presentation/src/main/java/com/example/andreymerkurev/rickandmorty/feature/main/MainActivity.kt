package com.example.andreymerkurev.rickandmorty.feature.main

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.example.andreymerkurev.rickandmorty.R
import com.example.andreymerkurev.rickandmorty.databinding.ActivityMainBinding
import com.example.andreymerkurev.rickandmorty.feature.ItemClickListener
import com.example.andreymerkurev.rickandmorty.feature.character.CharactersFragment
import com.example.andreymerkurev.rickandmorty.feature.characterfilter.CharactersFilterFragment
import com.example.andreymerkurev.rickandmorty.feature.charactersdetails.CharactersDetailsFragment
import com.example.andreymerkurev.rickandmorty.feature.episode.EpisodesFragment
import com.example.andreymerkurev.rickandmorty.feature.episodedetails.EpisodesDetailsFragment
import com.example.andreymerkurev.rickandmorty.feature.episodefilter.EpisodesFilterFragment
import com.example.andreymerkurev.rickandmorty.feature.location.LocationsFragment
import com.example.andreymerkurev.rickandmorty.feature.locationdetails.LocationsDetailsFragment
import com.example.andreymerkurev.rickandmorty.feature.locationfilter.LocationsFilterFragment


class MainActivity : AppCompatActivity(), ItemClickListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val actionBar = supportActionBar
        actionBar?.setHomeButtonEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        if (supportFragmentManager.findFragmentByTag(CharactersFragment.FRAGMENT_TAG) == null) {
            supportFragmentManager.beginTransaction().run {
                val fragment = CharactersFragment.newInstance(arrayListOf())
                replace(R.id.container, fragment, CharactersFragment.FRAGMENT_TAG)
                commit()
            }
        }

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.pageCharacters -> {
                    supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                    supportFragmentManager.beginTransaction().run {
                        val fragment = CharactersFragment.newInstance(arrayListOf())
                        replace(R.id.container, fragment, CharactersFragment.FRAGMENT_TAG)
                        commit()
                    }

                    return@setOnItemSelectedListener true
                }
                R.id.pageLocations -> {
                    supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                    supportFragmentManager.beginTransaction().run {
                        val fragment = LocationsFragment.newInstance(arrayListOf())
                        replace(R.id.container, fragment, LocationsFragment.FRAGMENT_TAG)
                        commit()
                    }

                    return@setOnItemSelectedListener true
                }
                else -> {
                    supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                    supportFragmentManager.beginTransaction().run {
                        val fragment = EpisodesFragment.newInstance(arrayListOf())
                        replace(R.id.container, fragment, EpisodesFragment.FRAGMENT_TAG)
                        commit()
                    }
                    return@setOnItemSelectedListener true
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        val fragmentManager: FragmentManager = supportFragmentManager
        if (fragmentManager.backStackEntryCount > 0)
            fragmentManager.popBackStack()
        else
            finish()
    }

    override fun onItemCharacterClick(characterId: Int) {
        supportFragmentManager.beginTransaction().run {
            val fragment = CharactersDetailsFragment.newInstance(characterId)
            replace(R.id.container, fragment, CharactersDetailsFragment.FRAGMENT_TAG)
            addToBackStack(CharactersDetailsFragment.FRAGMENT_TAG)
            commit()
        }
    }

    override fun onItemEpisodeClick(episodeId: Int) {
        supportFragmentManager.beginTransaction().run {
            val fragment = EpisodesDetailsFragment.newInstance(episodeId)
            replace(R.id.container, fragment, EpisodesDetailsFragment.FRAGMENT_TAG)
            addToBackStack(EpisodesDetailsFragment.FRAGMENT_TAG)
            commit()
        }
    }

    override fun onItemLocationClick(locationId: Int) {
        supportFragmentManager.beginTransaction().run {
            val fragment = LocationsDetailsFragment.newInstance(locationId)
            replace(R.id.container, fragment, LocationsDetailsFragment.FRAGMENT_TAG)
            addToBackStack(LocationsDetailsFragment.FRAGMENT_TAG)
            commit()
        }
    }

    override fun onCharacterFilterClick() {
        supportFragmentManager.beginTransaction().run {
            val fragment = CharactersFilterFragment.newInstance()
            replace(R.id.container, fragment, CharactersFilterFragment.FRAGMENT_TAG)
            addToBackStack(CharactersFilterFragment.FRAGMENT_TAG)
            commit()
        }
    }

    override fun onEpisodeFilterClick() {
        supportFragmentManager.beginTransaction().run {
            val fragment = EpisodesFilterFragment.newInstance()
            replace(R.id.container, fragment, EpisodesFilterFragment.FRAGMENT_TAG)
            addToBackStack(EpisodesFilterFragment.FRAGMENT_TAG)
            commit()
        }
    }

    override fun onLocationFilterClick() {
        supportFragmentManager.beginTransaction().run {
            val fragment = LocationsFilterFragment.newInstance()
            replace(R.id.container, fragment, LocationsFilterFragment.FRAGMENT_TAG)
            addToBackStack(LocationsFilterFragment.FRAGMENT_TAG)
            commit()
        }
    }

    override fun showFilteringCharactersClick(listParams: ArrayList<String>) {
        supportFragmentManager.beginTransaction().run {
            val fragment = CharactersFragment.newInstance(listParams)
            replace(R.id.container, fragment, CharactersFragment.FRAGMENT_TAG)
            commit()
        }
    }

    override fun showFilteringEpisodesClick(listParams: ArrayList<String>) {
        supportFragmentManager.beginTransaction().run {
            val fragment = EpisodesFragment.newInstance(listParams)
            replace(R.id.container, fragment, EpisodesFragment.FRAGMENT_TAG)
            commit()
        }
    }

    override fun showFilteringLocationsClick(listParams: ArrayList<String>) {
        supportFragmentManager.beginTransaction().run {
            val fragment = LocationsFragment.newInstance(listParams)
            replace(R.id.container, fragment, LocationsFragment.FRAGMENT_TAG)
            commit()
        }
    }

    override fun charactersRefresh() {
        supportFragmentManager.beginTransaction().run {
            val fragment = CharactersFragment.newInstance(arrayListOf())
            replace(R.id.container, fragment, CharactersFragment.FRAGMENT_TAG)
            commit()
        }
    }

    override fun episodesRefresh() {
        supportFragmentManager.beginTransaction().run {
            val fragment = EpisodesFragment.newInstance(arrayListOf())
            replace(R.id.container, fragment, EpisodesFragment.FRAGMENT_TAG)
            commit()
        }
    }

    override fun locationsRefresh() {
        supportFragmentManager.beginTransaction().run {
            val fragment = LocationsFragment.newInstance(arrayListOf())
            replace(R.id.container, fragment, LocationsFragment.FRAGMENT_TAG)
            commit()
        }
    }
}