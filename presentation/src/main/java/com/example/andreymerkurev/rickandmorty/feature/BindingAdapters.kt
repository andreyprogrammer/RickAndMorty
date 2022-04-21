package com.example.andreymerkurev.rickandmorty.feature

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.anderymerkurev.domain.entities.Characters
import com.example.anderymerkurev.domain.entities.Episodes
import com.example.andreymerkurev.rickandmorty.feature.charactersdetails.CharactersDetailsAdapter
import com.example.andreymerkurev.rickandmorty.feature.episodedetails.EpisodesDetailsAdapter
import com.squareup.picasso.Picasso


@BindingAdapter("imageUrl")
fun bindImage(imageView: ImageView, imageUrl: String) {
    if (imageUrl != "") {
        Picasso
            .get()
            .load(imageUrl)
            .into(imageView)
    }
}

@BindingAdapter("listEpisodes")
fun bindEpisodesRecyclerView(recyclerView: RecyclerView, data: List<Episodes>?) {
    val adapter = recyclerView.adapter as CharactersDetailsAdapter
    adapter.submitList(data)
}

@BindingAdapter("listCharacters")
fun bindCharactersRecyclerView(recyclerView: RecyclerView, data: List<Characters>?) {
    val adapter = recyclerView.adapter as EpisodesDetailsAdapter
    adapter.submitList(data)
}