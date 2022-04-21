package com.example.andreymerkurev.rickandmorty.feature.character

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.anderymerkurev.data.network.PicassoLoader
import com.example.anderymerkurev.domain.entities.Characters
import com.example.andreymerkurev.rickandmorty.databinding.ItemCharacterBinding

class CharactersAdapter(
    private val onClick: (Int) -> Unit,
    private val picassoLoader: PicassoLoader
    ) : PagingDataAdapter<Characters, CharactersAdapter.CharacterViewHolder>(DiffCallback) {

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = getItem(position)
        if (character != null) {
            holder.bind(character)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        return CharacterViewHolder(
            ItemCharacterBinding.inflate(LayoutInflater.from(parent.context)),
            onClick,
            picassoLoader
        )
    }

    object DiffCallback : DiffUtil.ItemCallback<Characters>() {

        override fun areItemsTheSame(oldItem: Characters, newItem: Characters): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Characters, newItem: Characters): Boolean {
            return oldItem == newItem
        }
    }

    class CharacterViewHolder(
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