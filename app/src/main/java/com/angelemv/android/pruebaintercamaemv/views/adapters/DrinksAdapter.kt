package com.angelemv.android.pruebaintercamaemv.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.angelemv.android.pruebaintercamaemv.R
import com.angelemv.android.pruebaintercamaemv.databinding.ItemListDrinksBinding
import com.angelemv.android.pruebaintercamaemv.models.data.Drink
import com.angelemv.android.pruebaintercamaemv.models.data.Favorito
import com.angelemv.android.pruebaintercamaemv.models.interfaces.FavoritoDao
import com.angelemv.android.pruebaintercamaemv.models.repository.FavRepository
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DrinksAdapter(
    private val favoritoDao: FavoritoDao,
    private val onItemClickListener: (Drink) -> Unit
) : ListAdapter<Drink, DrinksAdapter.DrinkViewHolder>(DrinkDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrinkViewHolder {
        val binding = ItemListDrinksBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DrinkViewHolder(binding, favoritoDao)
    }

    override fun onBindViewHolder(holder: DrinkViewHolder, position: Int) {
        val drink = getItem(position)
        holder.bind(drink)
        holder.itemView.setOnClickListener {
            onItemClickListener(drink)
        }
    }

    class DrinkViewHolder(
        private val binding: ItemListDrinksBinding,
        private val favoritoDao: FavoritoDao
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(drink: Drink) {
            binding.tvTittle.text = drink.strDrink
            binding.tvSubTittle.text = drink.strCategory

            Picasso.get()
                .load(drink.strDrinkThumb)
                .placeholder(R.drawable.ic_drink)
                .error(R.drawable.ic_error)
                .into(binding.imgDrink)

            binding.btnLike.setOnClickListener {
                binding.btnLike.isEnabled = false
                CoroutineScope(Dispatchers.IO).launch {
                    val existingFavorito = favoritoDao.getFavoritoByCocktailId(drink.idDrink)
                    val message: String
                    if (existingFavorito == null) {
                        val favorito = Favorito(
                            cocktailId = drink.idDrink,
                            name = drink.strDrink,
                            category = drink.strCategory,
                            image = drink.strDrinkThumb.toString()
                        )
                        favoritoDao.insert(favorito)
                        message = "Se agregó a favoritos: ${drink.strDrink}"
                    } else {
                        message = "Ya está en favoritos: ${drink.strDrink}"
                    }
                    withContext(Dispatchers.Main) {
                        Toast.makeText(binding.root.context, message, Toast.LENGTH_SHORT).show()
                        binding.btnLike.isEnabled = true
                    }
                }
            }
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

