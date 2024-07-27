package com.angelemv.android.pruebaintercamaemv.views.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Visibility
import com.angelemv.android.pruebaintercamaemv.R
import com.angelemv.android.pruebaintercamaemv.databinding.ItemListDrinksBinding
import com.angelemv.android.pruebaintercamaemv.models.data.Favorito
import com.squareup.picasso.Picasso

class FavAdapter : ListAdapter<Favorito, FavAdapter.FavViewHolder>(FavDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavViewHolder {
        val binding = ItemListDrinksBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavViewHolder, position: Int) {
        val favorito = getItem(position)
        holder.bind(favorito)
    }

    class FavViewHolder(private val binding: ItemListDrinksBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(favorito: Favorito) {
            binding.tvTittle.text = favorito.name
            binding.tvSubTittle.text = favorito.category
            binding.btnLike.visibility = View.GONE
            Picasso.get()
                .load(favorito.image)
                .placeholder(R.drawable.ic_drink)
                .error(R.drawable.ic_error)
                .into(binding.imgDrink)
        }
    }

    class FavDiffCallback : DiffUtil.ItemCallback<Favorito>() {
        override fun areItemsTheSame(oldItem: Favorito, newItem: Favorito): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Favorito, newItem: Favorito): Boolean {
            return oldItem == newItem
        }
    }
}