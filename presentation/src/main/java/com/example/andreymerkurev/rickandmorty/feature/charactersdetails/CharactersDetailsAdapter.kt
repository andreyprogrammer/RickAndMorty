package com.example.andreymerkurev.rickandmorty.feature.charactersdetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.anderymerkurev.domain.entities.Episodes
import com.example.andreymerkurev.rickandmorty.databinding.ItemEpisodeBinding

class CharactersDetailsAdapter(
    private val onClick: (Int) -> Unit
): ListAdapter<Episodes, CharactersDetailsAdapter.CharactersDetailsViewHolder>(DiffCallback)  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersDetailsViewHolder {
        return CharactersDetailsViewHolder(
            ItemEpisodeBinding.inflate(LayoutInflater.from(parent.context)),
            onClick)
    }

    override fun onBindViewHolder(holder: CharactersDetailsViewHolder, position: Int) {
        val episode = getItem(position)
        holder.bind(episode)
    }

    object DiffCallback : DiffUtil.ItemCallback<Episodes>() {
        override fun areItemsTheSame(old: Episodes, aNew: Episodes): Boolean {
            return old === aNew
        }

        override fun areContentsTheSame(old: Episodes, aNew: Episodes): Boolean {
            return old.id == aNew.id
        }
    }

    class CharactersDetailsViewHolder(
        private val binding: ItemEpisodeBinding,
        private val onClick: (Int) -> Unit
    ) : RecyclerView.ViewHolder(
        binding.root
    ) {
        fun bind(episode: Episodes) {
            binding.itemEpisodeName.text = episode.name
            binding.itemEpisodeNumber.text = episode.episode
            binding.itemEpisodeAirDate.text = episode.airDate
            itemView.setOnClickListener {
                if (episode.id != 0) onClick(episode.id)
            }
        }
    }
}