package com.angelemv.android.pruebaintercamaemv.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.angelemv.android.pruebaintercamaemv.R
import com.angelemv.android.pruebaintercamaemv.databinding.FragmentListBinding

class ListFragment : Fragment() {
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindingConf()
    }

    private fun bindingConf() {
        binding.btnFragmentoFavs.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_favFragment)
        }
        binding.btnFragmentoDetalles.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_detailFragment)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}