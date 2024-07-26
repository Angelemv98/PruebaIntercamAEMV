package com.angelemv.android.pruebaintercamaemv.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.angelemv.android.pruebaintercamaemv.R
import com.angelemv.android.pruebaintercamaemv.databinding.FragmentMainBinding
import com.angelemv.android.pruebaintercamaemv.viewmodel.LoginViewModel
import com.squareup.picasso.Picasso

class LoginFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindingConf()
    }

    private fun bindingConf() {
        binding.let {
            Picasso.get().load("https://cdn.icon-icons.com/icons2/3398/PNG/512/android_logo_icon_214673.png").into(it.imageViewIcon)
            it.btnLogin.setOnClickListener {
                viewModel.setUser(binding.etUser.text.toString())
                viewModel.setPassword(binding.etPassword.text.toString())
                if (!viewModel.validateUser()){
                    binding.errorLabel.visibility = View.VISIBLE
                }
                else {
                    binding.tilUser.error = null
                    binding.tilPassword.error = null
                    binding.etUser.setText("")
                    binding.etPassword.setText("")
                    findNavController().navigate(R.id.action_mainFragment_to_listFragment)
                }

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}