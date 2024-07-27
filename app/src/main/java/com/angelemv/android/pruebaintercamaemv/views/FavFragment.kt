package com.angelemv.android.pruebaintercamaemv.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.angelemv.android.pruebaintercamaemv.databinding.FragmentFavBinding
import com.angelemv.android.pruebaintercamaemv.models.data.AppDatabase
import com.angelemv.android.pruebaintercamaemv.models.repository.FavRepository
import com.angelemv.android.pruebaintercamaemv.viewmodel.FavViewModel
import com.angelemv.android.pruebaintercamaemv.viewmodel.FavViewModelFactory
import com.angelemv.android.pruebaintercamaemv.views.adapters.FavAdapter

class FavFragment : Fragment() {
    private var _binding: FragmentFavBinding? = null
    private val binding get() = _binding!!
    private lateinit var favAdapter: FavAdapter

    private val viewModel: FavViewModel by viewModels {
        FavViewModelFactory(FavRepository(AppDatabase.getDatabase(requireContext()).favoritoDao()))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeFavs()
    }

    private fun setupRecyclerView() {
        favAdapter = FavAdapter()
        binding.recyclerViewFavs.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewFavs.adapter = favAdapter
    }

    private fun observeFavs() {
        viewModel.favs.observe(viewLifecycleOwner, Observer { favs ->
            favs?.let {
                favAdapter.submitList(it)
                if (it.isEmpty()) {
                    showNoFavoritesMessage()
                } else {
                    hideNoFavoritesMessage()
                }
            }
        })

        viewModel.errorMessage.observe(viewLifecycleOwner) { error ->
            error?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun showNoFavoritesMessage() {
        binding.textViewNoFavorites.visibility = View.VISIBLE
        binding.recyclerViewFavs.visibility = View.GONE
    }

    private fun hideNoFavoritesMessage() {
        binding.textViewNoFavorites.visibility = View.GONE
        binding.recyclerViewFavs.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
