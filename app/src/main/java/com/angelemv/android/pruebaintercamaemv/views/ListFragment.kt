package com.angelemv.android.pruebaintercamaemv.views

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
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
import androidx.recyclerview.widget.RecyclerView
import com.angelemv.android.pruebaintercamaemv.R
import com.angelemv.android.pruebaintercamaemv.databinding.FragmentListBinding
import com.angelemv.android.pruebaintercamaemv.models.data.AppDatabase
import com.angelemv.android.pruebaintercamaemv.models.data.Drink
import com.angelemv.android.pruebaintercamaemv.models.data.toBundle
import com.angelemv.android.pruebaintercamaemv.models.interfaces.RetrofitInstance
import com.angelemv.android.pruebaintercamaemv.models.repository.DrinksRepository
import com.angelemv.android.pruebaintercamaemv.viewmodel.DrinksViewModel
import com.angelemv.android.pruebaintercamaemv.viewmodel.DrinksViewModelFactory
import com.angelemv.android.pruebaintercamaemv.views.adapters.DrinksAdapter

class ListFragment : Fragment() {
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private lateinit var drinksAdapter: DrinksAdapter

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

        val toolbar: Toolbar = binding.toolbar
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)
        toolbar.title = "Bebidas"
        setHasOptionsMenu(true)

        setupRecyclerView()
        observeDrinks()
        viewModel.loadNextDrinks()
    }

    private fun setupRecyclerView() {
        val favoritoDao = AppDatabase.getDatabase(requireContext()).favoritoDao()
        drinksAdapter = DrinksAdapter(favoritoDao) { drink ->
            val bundle = drink.toBundle()
            findNavController().navigate(R.id.action_listFragment_to_detailFragment, bundle)
        }
        binding.recyclerViewDrinks.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewDrinks.adapter = drinksAdapter

        binding.recyclerViewDrinks.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = binding.recyclerViewDrinks.layoutManager as LinearLayoutManager
                if (layoutManager.findLastVisibleItemPosition() == drinksAdapter.itemCount - 1) {
                    viewModel.loadNextDrinks()
                }
            }
        })
    }

    private fun observeDrinks() {
        viewModel.drinks.observe(viewLifecycleOwner, Observer { drinks ->
            binding.progressBar.visibility = View.GONE
            drinks?.let {
                drinksAdapter.submitList(it)
            }
        })

        viewModel.isLoading.observe(viewLifecycleOwner) { loading ->
            binding.progressBar.visibility = if (loading) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { error ->
            error?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_fav -> {
                findNavController().navigate(R.id.action_listFragment_to_favFragment)
                true
            }
            R.id.action_exit -> {
                showExitDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showExitDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Aviso")
        builder.setMessage("¿Deseas salir de la aplicación?")
        builder.setPositiveButton("Aceptar") { _, _ ->
            requireActivity().finish()
        }
        builder.setNegativeButton("Cancelar") { dialog, _ ->
            dialog.dismiss()
        }
        builder.create().show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}