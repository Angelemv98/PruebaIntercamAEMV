package com.angelemv.android.pruebaintercamaemv.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.angelemv.android.pruebaintercamaemv.R
import com.angelemv.android.pruebaintercamaemv.databinding.FragmentListBinding
import com.angelemv.android.pruebaintercamaemv.models.interfaces.RetrofitInstance
import com.angelemv.android.pruebaintercamaemv.models.repository.DrinksRepository
import com.angelemv.android.pruebaintercamaemv.viewmodel.DrinksViewModel
import com.angelemv.android.pruebaintercamaemv.viewmodel.DrinksViewModelFactory
import com.angelemv.android.pruebaintercamaemv.views.adapters.DrinksAdapter

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
        bindingConf()
        viewModel.searchDrinks("a") // Llamar a la API al iniciar
    }

    private fun setupRecyclerView() {
        drinksAdapter = DrinksAdapter()
        binding.recyclerViewDrinks.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewDrinks.adapter = drinksAdapter
    }

    private fun observeDrinks() {
        viewModel.drinks.observe(viewLifecycleOwner, Observer { drinks ->
            drinks?.let {
                drinksAdapter.submitList(it)
            }
        })
    }

    private fun bindingConf() {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
