package com.example.andreymerkurev.rickandmorty.feature

interface ItemClickListener {
    fun onItemCharacterClick(characterId: Int)
    fun onItemEpisodeClick(episodeId: Int)
    fun onItemLocationClick(locationId: Int)
    fun showFilteringCharactersClick(listParams: ArrayList<String>)
    fun showFilteringEpisodesClick(listParams: ArrayList<String>)
    fun showFilteringLocationsClick(listParams: ArrayList<String>)
    fun onCharacterFilterClick()
    fun onEpisodeFilterClick()
    fun onLocationFilterClick()
    fun charactersRefresh()
    fun episodesRefresh()
    fun locationsRefresh()
}