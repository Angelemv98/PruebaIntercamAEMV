package com.angelemv.android.pruebaintercamaemv.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.angelemv.android.pruebaintercamaemv.R
import com.angelemv.android.pruebaintercamaemv.databinding.FragmentDetailBinding
import com.angelemv.android.pruebaintercamaemv.models.data.Drink
import com.angelemv.android.pruebaintercamaemv.models.data.getFormattedIngredients
import com.angelemv.android.pruebaintercamaemv.models.data.toDrink
import com.squareup.picasso.Picasso

// FavFragment.kt
class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var drink: Drink

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
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
                // Usa el objeto drink para poblar tu UI
                bin.etNameDrink.text = drink.strDrink
                bin.tvIngedients.text = drink.getFormattedIngredients()
                if (drink.strInstructionsES!=null){
                bin.tvInstructions.text = drink.strInstructionsES
                }
                else{
                    bin.tvInstructions.text = drink.strInstructions
                }

                // Cargar imagen usando Picasso
                Picasso.get()
                    .load(drink.strDrinkThumb)
                    .placeholder(R.drawable.ic_login) // Imagen de placeholder
                    .error(R.drawable.ic_error) // Imagen de error
                    .into(bin.imageDrink)
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
