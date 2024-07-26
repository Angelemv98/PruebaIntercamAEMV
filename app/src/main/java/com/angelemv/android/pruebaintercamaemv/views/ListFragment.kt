// ListFragment.kt
package com.angelemv.android.pruebaintercamaemv.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.angelemv.android.pruebaintercamaemv.R
import com.angelemv.android.pruebaintercamaemv.databinding.FragmentListBinding
import com.angelemv.android.pruebaintercamaemv.models.data.Drink
import com.angelemv.android.pruebaintercamaemv.models.data.toBundle
import com.angelemv.android.pruebaintercamaemv.models.interfaces.RetrofitInstance
import com.angelemv.android.pruebaintercamaemv.models.repository.DrinksRepository
import com.angelemv.android.pruebaintercamaemv.viewmodel.DrinksViewModel
import com.angelemv.android.pruebaintercamaemv.viewmodel.DrinksViewModelFactory
import com.angelemv.android.pruebaintercamaemv.views.adapters.DrinksAdapter

// ListFragment.kt
class ListFragment : Fragment() {
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private lateinit var drinksAdapter: DrinksAdapter

    // Instanciar el ViewModel usando el Factory
    private val viewModel: DrinksViewModel by viewModels {
        DrinksViewModelFactory(DrinksRepository(RetrofitInstance.api))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeDrinks()
        setupToolbar()
        viewModel.searchDrinks("a") // Llamar a la API al iniciar
    }

    private fun setupToolbar() {
        val toolbar: Toolbar = binding.toolbar
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        toolbar.title = "Bebidas"
        toolbar.setTitleTextColor(resources.getColor(R.color.white))
    }

    private fun setupRecyclerView() {
        drinksAdapter = DrinksAdapter { drink ->
            val bundle = drink.toBundle()
            findNavController().navigate(R.id.action_listFragment_to_detailFragment, bundle)
        }
        binding.recyclerViewDrinks.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewDrinks.adapter = drinksAdapter
    }

    private fun observeDrinks() {
        viewModel.drinks.observe(viewLifecycleOwner, Observer { drinks ->
            binding.progressBar.visibility = View.GONE // Ocultar el loader
            drinks?.let {
                drinksAdapter.submitList(it)
            }
        })

        viewModel.isLoading.observe(viewLifecycleOwner) { loading ->
            binding.progressBar.visibility = if (loading) {
                View.VISIBLE // Mostrar el loader
            } else {
                View.GONE // Ocultar el loader
            }
        }
        // Observar mensajes de error
        viewModel.errorMessage.observe(viewLifecycleOwner) { error ->
            error?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}



