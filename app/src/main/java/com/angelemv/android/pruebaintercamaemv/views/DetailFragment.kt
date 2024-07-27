package com.angelemv.android.pruebaintercamaemv.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.angelemv.android.pruebaintercamaemv.R
import com.angelemv.android.pruebaintercamaemv.databinding.FragmentDetailBinding
import com.angelemv.android.pruebaintercamaemv.models.data.AppDatabase
import com.angelemv.android.pruebaintercamaemv.models.data.Drink
import com.angelemv.android.pruebaintercamaemv.models.data.Favorito
import com.angelemv.android.pruebaintercamaemv.models.data.getFormattedIngredients
import com.angelemv.android.pruebaintercamaemv.models.data.toDrink
import com.angelemv.android.pruebaintercamaemv.models.interfaces.FavoritoDao
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var drink: Drink
    private lateinit var favoritoDao: FavoritoDao
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        favoritoDao = AppDatabase.getDatabase(requireContext()).favoritoDao()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindingConf()
    }

    private fun bindingConf() {
        binding.let { bin ->
            arguments?.let {
                drink = it.toDrink()
                bin.etNameDrink.text = drink.strDrink
                bin.tvIngedients.text = drink.getFormattedIngredients()
                if (drink.strInstructionsES != null) {
                    bin.tvInstructions.text = drink.strInstructionsES
                } else {
                    bin.tvInstructions.text = drink.strInstructions
                }
                Picasso.get()
                    .load(drink.strDrinkThumb)
                    .placeholder(R.drawable.ic_login)
                    .error(R.drawable.ic_error)
                    .into(bin.imageDrink)
            }
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

            bin.ibExit.setOnClickListener {
                requireActivity().onBackPressed()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
