package com.example.andreymerkurev.rickandmorty.feature.episodedetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.anderymerkurev.data.network.PicassoLoader
import com.example.anderymerkurev.domain.entities.Characters
import com.example.andreymerkurev.rickandmorty.databinding.ItemCharacterBinding

class EpisodesDetailsAdapter (
    private val onClick: (Int) -> Unit,
    private val picassoLoader: PicassoLoader
): ListAdapter<Characters, EpisodesDetailsAdapter.EpisodesDetailsViewHolder>(DiffCallback)  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodesDetailsViewHolder {
        return EpisodesDetailsViewHolder(
            ItemCharacterBinding.inflate(LayoutInflater.from(parent.context)),
            onClick,
            picassoLoader
        )
    }

    override fun onBindViewHolder(holder: EpisodesDetailsViewHolder, position: Int) {
        val character = getItem(position)
        holder.bind(character)
    }

    object DiffCallback : DiffUtil.ItemCallback<Characters>() {
        override fun areItemsTheSame(old: Characters, aNew: Characters): Boolean {
            return old === aNew
        }

        override fun areContentsTheSame(old: Characters, aNew: Characters): Boolean {
            return old.id == aNew.id
        }
    }

    class EpisodesDetailsViewHolder(
        private val binding: ItemCharacterBinding,
        private val onClick: (Int) -> Unit,
        private val picassoLoader: PicassoLoader
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(character: Characters) {
            itemView.setOnClickListener {
                if (character.id != 0) onClick(character.id)
            }
            binding.itemCharacterName.text = character.name
            binding.itemCharacterSpecies.text = character.species
            binding.itemCharacterStatus.text = character.status
            binding.itemCharacterGender.text = character.gender
            picassoLoader.loadImage(character.image, binding.itemCharacterImage)
        }
    }
}