package com.example.andreymerkurev.rickandmorty.feature.location

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.anderymerkurev.domain.entities.Locations
import com.example.andreymerkurev.rickandmorty.databinding.ItemLocationBinding

class LocationsAdapter(
    private val onClick: (Int) -> Unit
) : PagingDataAdapter<Locations, LocationsAdapter.LocationsViewHolder>(DiffCallback) {

    override fun onBindViewHolder(holder: LocationsViewHolder, position: Int) {
        val location = getItem(position)
        if (location != null) {
            holder.bind(location)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationsViewHolder {
        return LocationsViewHolder(
            ItemLocationBinding.inflate(LayoutInflater.from(parent.context)),
            onClick
        )
    }

    object DiffCallback : DiffUtil.ItemCallback<Locations>() {

        override fun areItemsTheSame(oldItem: Locations, newItem: Locations): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Locations, newItem: Locations): Boolean {
            return oldItem == newItem
        }
    }

    class LocationsViewHolder(
        private val binding: ItemLocationBinding,
        private val onClick: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(location: Locations) {
            itemView.setOnClickListener {
                if (location.id != 0) onClick(location.id)
            }
            binding.itemLocationName.text = location.name
            binding.itemLocationType.text = location.type
            binding.itemLocationDimension.text = location.dimension
        }
    }
}