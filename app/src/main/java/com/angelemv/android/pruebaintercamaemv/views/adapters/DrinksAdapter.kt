// DrinksAdapter.kt
package com.angelemv.android.pruebaintercamaemv.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.angelemv.android.pruebaintercamaemv.R
import com.angelemv.android.pruebaintercamaemv.databinding.ItemListDrinksBinding
import com.angelemv.android.pruebaintercamaemv.models.data.Drink
import com.squareup.picasso.Picasso

class DrinksAdapter(private val onItemClickListener: (Drink) -> Unit) :
    ListAdapter<Drink, DrinksAdapter.DrinkViewHolder>(DrinkDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrinkViewHolder {
        val binding = ItemListDrinksBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DrinkViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DrinkViewHolder, position: Int) {
        val drink = getItem(position)
        holder.bind(drink)
        holder.itemView.setOnClickListener {
            onItemClickListener(drink)
        }
    }

    class DrinkViewHolder(private val binding: ItemListDrinksBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(drink: Drink) {
            binding.tvTittle.text = drink.strDrink
            binding.tvSubTittle.text = drink.strCategory

            // Usar Picasso para cargar la imagen
            Picasso.get()
                .load(drink.strDrinkThumb)
                .placeholder(R.drawable.ic_login) // Imagen de placeholder
                .error(R.drawable.ic_error) // Imagen de error
                .into(binding.imgDrink)
        }
    }

    class DrinkDiffCallback : DiffUtil.ItemCallback<Drink>() {
        override fun areItemsTheSame(oldItem: Drink, newItem: Drink): Boolean {
            return oldItem.idDrink == newItem.idDrink
        }

        override fun areContentsTheSame(oldItem: Drink, newItem: Drink): Boolean {
            return oldItem == newItem
        }
    }
}


