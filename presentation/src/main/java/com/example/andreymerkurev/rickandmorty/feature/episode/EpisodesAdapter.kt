package com.example.andreymerkurev.rickandmorty.feature.episode

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.anderymerkurev.domain.entities.Episodes
import com.example.andreymerkurev.rickandmorty.databinding.ItemEpisodeBinding

class EpisodesAdapter(
    private val onClick: (Int) -> Unit
) : PagingDataAdapter<Episodes, EpisodesAdapter.EpisodesViewHolder>(DiffCallback) {

    override fun onBindViewHolder(holder: EpisodesViewHolder, position: Int) {
        val episode = getItem(position)
        if (episode != null) {
            holder.bind(episode)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodesViewHolder {
        return EpisodesViewHolder(
            ItemEpisodeBinding.inflate(LayoutInflater.from(parent.context)),
            onClick
        )
    }

    object DiffCallback : DiffUtil.ItemCallback<Episodes>() {

        override fun areItemsTheSame(oldItem: Episodes, newItem: Episodes): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Episodes, newItem: Episodes): Boolean {
            return oldItem == newItem
        }
    }

    class EpisodesViewHolder(
        private val binding: ItemEpisodeBinding,
        private val onClick: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(episode: Episodes) {
            itemView.setOnClickListener { onClick(episode.id) }
            binding.itemEpisodeNumber.text = episode.episode
            binding.itemEpisodeName.text = episode.name
            binding.itemEpisodeAirDate.text = episode.airDate
        }
    }
}